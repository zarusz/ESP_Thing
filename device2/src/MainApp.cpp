//
//
//

#include "MainApp.h"
#include <stdio.h>
#include <pb_encode.h>
#include <pb_decode.h>
#include "FeatureControllers/SwitchFeatureController.h"
#include "FeatureControllers/TempFeatureController.h"

#define DEVICE_UNIQUE_ID "dev_sufit"
#define TOPIC_DEVICE_EVENTS "device/events"
#define TOPIC_DEVICE_DESCRIPTION "device/description"

MainApp::MainApp(MQTT_CALLBACK_SIGNATURE)
	: pubSubClient(espClient),
		deviceConfig(DEVICE_UNIQUE_ID, "WareHouse_24GHz", "bonifacy", "raspberrypi")
{
	features.push_back(new SwitchFeatureController(10, this, 0, false));
	features.push_back(new TempFeatureController(30, 31, this, 2));

	deviceInTopic = String("device/") + deviceConfig.uniqueId;

	pubSubClient.setServer(deviceConfig.mqttBroker, 1883);
	pubSubClient.setCallback(callback);
}

MainApp::~MainApp()
{
	// std::vector<SwitchFeatureController*>::iterator
	for(auto it = features.begin(); it != features.end(); ++it)
	{
    delete *it;
	}
}

void MainApp::Init()
{
	pinMode(BUILTIN_LED, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
	Serial.begin(115200);

	SetupWifi();
}

void MainApp::Loop()
{
	if (!pubSubClient.connected()) {
		ReconnectPubSub();
	}
	pubSubClient.loop();
	OnLoop();
}

void MainApp::SetupWifi()
{
	delay(10);
	// We start by connecting to a WiFi network
	Serial.println();
	Serial.print("Connecting to ");
	Serial.println(deviceConfig.networkName);

	WiFi.begin(deviceConfig.networkName, deviceConfig.networkPassword);
	while (WiFi.status() != WL_CONNECTED) {
		delay(500);
		Serial.print(".");
	}

	Serial.println("");
	Serial.println("WiFi connected");
	Serial.println("IP address: ");
	Serial.println(WiFi.localIP());
}

void MainApp::ReconnectPubSub()
{
	// Loop until we're reconnected
	while (!pubSubClient.connected())
	{
		Serial.print("Attempting MQTT connection...");
		// Attempt to connect
		if (pubSubClient.connect(deviceConfig.uniqueId))
		{
			Serial.println("Connected to MQTT broker.");
			// ... and resubscribe
			pubSubClient.subscribe(deviceInTopic.c_str());
			OnStart();
		}
		else
		{
			Serial.print("failed, rc=");
			Serial.print(pubSubClient.state());
			Serial.println(" try again in 5 seconds");
			// Wait 5 seconds before retrying
			delay(5000);
		}
	}
}

void MainApp::Callback(char* topic, byte* payload, unsigned int length)
{
	if (deviceInTopic == topic) {
		/* Allocate space for the decoded message. */
		DeviceMessage message = DeviceMessage_init_zero;
		bool status = DecodeMessage(payload, length, DeviceMessage_fields, &message);
		if (status)
		{
			HandleDeviceMessage(message);
		}
		return;
	}

	DebugRetrievedMessage(topic, payload, length);
}

bool MainApp::DecodeMessage(byte* payload, unsigned int length, const pb_field_t* msg_fields, void* msg) const
{
	 /* Create a stream that reads from the buffer. */
	pb_istream_t stream = pb_istream_from_buffer(payload, length);
	/* Now we are ready to decode the message. */
	bool status = pb_decode(&stream, msg_fields, msg);

	/* Check for errors... */
	if (!status)
	{
			Serial.print("Decoding failed:");
			Serial.println(PB_GET_ERROR(&stream));
	}
	return status;
}

bool MainApp::EncodeMessage(byte* payload, unsigned int maxLength, unsigned int& length, const pb_field_t* msg_fields, const void* msg) const
{
	/* Create a stream that will write to our buffer. */
	pb_ostream_t stream = pb_ostream_from_buffer(payload, maxLength);
	/* Now we are ready to encode the message! */
  bool status = pb_encode(&stream, msg_fields, msg);
  length = stream.bytes_written;

	/* Then just check for any errors.. */
	if (!status)
	{
			Serial.print("Encoding failed: ");
			Serial.println(PB_GET_ERROR(&stream));
	}

	return status;
}

bool MainApp::PublishMessage(const char* topic, const pb_field_t* msg_fields, const void* msg)
{
	byte buffer[512];
	unsigned int length;

	if (!EncodeMessage(buffer, sizeof(buffer), length, msg_fields, msg))
	{
			return false;
	}

	pubSubClient.publish(topic, buffer, length);
	return true;
}

void MainApp::DebugRetrievedMessage(const char* topic, byte* payload, unsigned int length)
{
	Serial.print("Message arrived [");
	Serial.print(topic);
	Serial.print("] ");
	for (int i = 0; i < length; i++)
	{
		Serial.print((char)payload[i]);
	}
	Serial.println();

	// Switch on the LED if an 1 was received as first character
	if ((char)payload[0] == '1')
	{
		digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
										  // but actually the LED is on; this is because
										  // it is acive low on the ESP-01)
	}
	else
	{
		digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
	}
}

void MainApp::HandleDeviceMessage(DeviceMessage& deviceMessage)
{
	Serial.println("HandleDeviceMessage (start)");
	for(auto it = features.begin(); it != features.end(); ++it) {
		(*it)->Handle(deviceMessage);
	}
	Serial.println("HandleDeviceMessage (finish)");
	// TODO send ACK Message back to sender
}

void MainApp::OnStart() {
	Serial.println("Starting...");

	Serial.println("Sending DeviceConnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceConnectedEvent = true;
	strcpy(deviceEvents.deviceConnectedEvent.device_id, deviceConfig.uniqueId);
	PublishMessage(TOPIC_DEVICE_EVENTS, DeviceEvents_fields, &deviceEvents);

	Serial.println("Sending DeviceDescription");
	deviceDescription = (DeviceDescription) DeviceDescription_init_zero;
	strcpy(deviceDescription.device_id, deviceConfig.uniqueId);
	deviceDescription.ports[0] = (DevicePort) DevicePort_init_default;
	deviceDescription.ports[0].port = 1;
	deviceDescription.ports[0].feature = DevicePortFeature::DevicePortFeature_SWITCH;
	deviceDescription.ports[1] = (DevicePort) DevicePort_init_default;
	deviceDescription.ports[1].port = 2;
	deviceDescription.ports[1].feature = DevicePortFeature::DevicePortFeature_SWITCH;
	PublishMessage(TOPIC_DEVICE_DESCRIPTION, DeviceDescription_fields, &deviceDescription);

	Serial.println("Started.");
}

void MainApp::OnStop() {
	Serial.println("Stopping...");

	Serial.println("Sending DeviceDisconnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceDisconnectedEvent = true;
	strcpy(deviceEvents.deviceDisconnectedEvent.device_id, deviceConfig.uniqueId);
	PublishMessage(TOPIC_DEVICE_EVENTS, DeviceEvents_fields, &deviceEvents);

	Serial.println("Stopped.");
}

void MainApp::OnLoop() {
	long now = millis();
	if (now - lastMsg > 10000) {
		lastMsg = now;
		++value;
		Serial.println(String("Publish DeviceHearbeatEvent. ") + value);

		DeviceEvents deviceEvents = DeviceEvents_init_zero;
		deviceEvents.has_deviceHearbeatEvent = true;
		strcpy(deviceEvents.deviceHearbeatEvent.device_id, deviceConfig.uniqueId);
		deviceEvents.deviceHearbeatEvent.sequence_id = value;
		PublishMessage(TOPIC_DEVICE_EVENTS, DeviceEvents_fields, &deviceEvents);
	}

	for (auto it = features.begin(); it != features.end(); ++it)
	{
		(*it)->Loop();
	}
}
