//
//
//

#include "MainApp.h"
#include <stdio.h>
#include <pb_encode.h>
#include <pb_decode.h>

MainApp::MainApp(MQTT_CALLBACK_SIGNATURE)
	: pubSubClient(espClient),
		deviceConfig("dev_sufit", "WareHouse_24GHz", "bonifacy", "raspberrypi")
{
  deviceInTopic = String("device/") + deviceConfig.uniqueId;

	pubSubClient.setServer(deviceConfig.mqttBroker, 1883);
	pubSubClient.setCallback(callback);
}

MainApp::~MainApp()
{
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

	long now = millis();
	if (now - lastMsg > 2000) {
		lastMsg = now;
		++value;
		snprintf(msg, 75, "hello world #%ld", value);
		Serial.print("Publish message: ");
		Serial.println(msg);

		pubSubClient.publish("outTopic", msg);
	}
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
			Serial.println("connected");
			// Once connected, publish an announcement...
			pubSubClient.publish("outTopic", "hello world");
			// ... and resubscribe
			pubSubClient.subscribe(deviceInTopic.c_str());
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
		DeviceMessage message;
		bool status = DecodeMessage(payload, length, message);
		if (status)
		{
			HandleDeviceMessage(message);
		}
		return;
	}

	DebugRetrievedMessage(topic, payload, length);
}

bool MainApp::DecodeMessage(byte* payload, unsigned int length, DeviceMessage& deviceMessage) const
{
	deviceMessage = DeviceMessage_init_zero;
	 /* Create a stream that reads from the buffer. */
	pb_istream_t stream = pb_istream_from_buffer(payload, length);
	/* Now we are ready to decode the message. */
	bool status = pb_decode(&stream, DeviceMessage_fields, &deviceMessage);
	/* Check for errors... */
	if (!status)
	{
			Serial.print("Decoding failed:");
			Serial.println(PB_GET_ERROR(&stream));
	}
	return status;
}

void MainApp::DebugRetrievedMessage(char* topic, byte* payload, unsigned int length)
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
	if (deviceMessage.has_switchCommand)
	{
		DeviceSwitchCommand* cmd = &deviceMessage.switchCommand;

		String msg = String("Recieved SwitchCommand for port ") + cmd->port + " with " + (cmd->on ? "turn on" : "turn off");
		Serial.println(msg);
		if (cmd->on)
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

	// TODO send ACK Message back to sender
}
