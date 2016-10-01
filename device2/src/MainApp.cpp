#include "MainApp.h"
#include <stdio.h>
#include <algorithm>
#include "Utils/TimeUtil.h"
#include "FeatureControllers/SwitchFeatureController.h"
#include "FeatureControllers/TempFeatureController.h"
#include "FeatureControllers/IRTransceiverFeatureController.h"
#include "FeatureControllers/IRReceiverFeatureController.h"

#define DEVICE_UNIQUE_ID "dev_sufit_new"
#define TOPIC_DEVICE_EVENTS "device/events"
#define TOPIC_DEVICE_DESCRIPTION "device/description"

MainApp::MainApp()
	: _deviceConfig(DEVICE_UNIQUE_ID, "WareHouse_24GHz", "bonifacy", "raspberrypi"),
		_pins(13, 14, 12, 20),
		_messageBus(_deviceConfig.mqttBroker, 1883, this, _deviceConfig.uniqueId, _serializer),
		_deviceInTopic(String("device/") + _deviceConfig.uniqueId)
{
	_features.push_back(new SwitchFeatureController(10, this, 20, false));
	_features.push_back(new SwitchFeatureController(11, this, 21, false));
	_features.push_back(new SwitchFeatureController(12, this, 22, false));
	_features.push_back(new SwitchFeatureController(13, this, 23, false));
	_features.push_back(new SwitchFeatureController(14, this, 24, false));
	_features.push_back(new SwitchFeatureController(15, this, 25, false));
	_features.push_back(new SwitchFeatureController(16, this, 26, false));
	_features.push_back(new SwitchFeatureController(17, this, 27, false));

	_features.push_back(new TempFeatureController(30, 31, this, 2, TOPIC_DEVICE_EVENTS));
	_features.push_back(new IRReceiverFeatureController(41, this, 4, TOPIC_DEVICE_EVENTS));
	_features.push_back(new IRTransceiverFeatureController(40, this, 16));
	_features.push_back(new IRTransceiverFeatureController(50, this, 5));
}

MainApp::~MainApp()
{
	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
    delete feature;
	});
	_features.clear();
}

void MainApp::Init()
{
	Serial.begin(115200);
	pinMode(LED_BUILTIN, OUTPUT);     // Initialize the BUILTIN_LED pin as an output

	SetupWifi();
}

void MainApp::Loop()
{
	_messageBus.Loop();

	if (!_started) {
		OnStart();
		_started = true;
	}

	OnLoop();
}

void MainApp::SetupWifi()
{
	// We start by connecting to a WiFi network
	Serial.println();
	Serial.print("Connecting to ");
	Serial.println(_deviceConfig.networkName);

	//WiFi.mode(WIFI_STA);
	WiFi.begin(_deviceConfig.networkName, _deviceConfig.networkPassword);
	while (WiFi.status() != WL_CONNECTED)
	{
		delay(500);
		Serial.print(".");
	}

	Serial.println("");
	Serial.println("WiFi connected");
	Serial.print("IP address: ");
	Serial.println(WiFi.localIP());
}

void MainApp::Handle(const char* topic, const std::vector<byte>& payload, Serializer& serializer)
{
	if (_deviceInTopic == topic)
	{
		DeviceMessage deviceMessage;
		PbMessage message(DeviceMessage_fields, &deviceMessage);

		if (!serializer.Decode(payload, &message))
		{
			Serial.println("Could not deserialize message.");
			return;
		}
		HandleDeviceMessage(deviceMessage);
		return;
	}

	//DebugRetrievedMessage(topic, message);
}

void MainApp::DebugRetrievedMessage(const char* topic, const void* message)
{
	/*
	Serial.print("Message arrived [");
	Serial.print(topic);
	Serial.print("] ");
	for (int i = 0; i < length; i++)
	{
		Serial.print((char)message[i]);
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
	*/
}

void MainApp::HandleDeviceMessage(const DeviceMessage& message)
{
	Serial.println("HandleDeviceMessage (start)");

	std::for_each(_features.begin(), _features.end(), [&message](FeatureController* feature) {
		feature->TryHandle(message);
	});

	Serial.println("HandleDeviceMessage (finish)");
	// TODO send ACK Message back to sender
}

void MainApp::SendDescription()
{
	Serial.println("Sending DeviceDescription");

	DeviceDescription deviceDescription = DeviceDescription_init_zero;
	strcpy(deviceDescription.device_id, _deviceConfig.uniqueId);

	int i = 0;
	std::for_each(_features.begin(), _features.end(), [&i, &deviceDescription](FeatureController* feature) {
		Serial.printf("---- port: %d", i);
		feature->Describe(deviceDescription.ports[i]);
		i++;
	});
	deviceDescription.ports_count = i;
	Serial.printf("---- ports total: %d", i);

	PbMessage message(DeviceDescription_fields, &deviceDescription);
	_messageBus.Publish(TOPIC_DEVICE_DESCRIPTION, &message);
}

void MainApp::OnStart()
{
	Serial.println("Starting...");

	Serial.println("Sending DeviceConnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceConnectedEvent = true;
	strcpy(deviceEvents.deviceConnectedEvent.device_id, _deviceConfig.uniqueId);

	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);

	SendDescription();

	Serial.println("Started.");
}

void MainApp::OnStop()
{
	Serial.println("Stopping...");

	Serial.println("Sending DeviceDisconnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceDisconnectedEvent = true;
	strcpy(deviceEvents.deviceDisconnectedEvent.device_id, _deviceConfig.uniqueId);

	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);

	Serial.println("Stopped.");
}

void MainApp::OnLoop()
{
	if (TimeUtil::IntervalPassed(_lastMsg, 10000))
	{
		++_value;
		Serial.printf("Publish DeviceHearbeatEvent %d\n", _value);

		DeviceEvents deviceEvents = DeviceEvents_init_zero;
		deviceEvents.has_deviceHearbeatEvent = true;
		strcpy(deviceEvents.deviceHearbeatEvent.device_id, _deviceConfig.uniqueId);
		deviceEvents.deviceHearbeatEvent.sequence_id = _value;

		PbMessage message(DeviceEvents_fields, &deviceEvents);
		_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
	}

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
		feature->Loop();
	});
}
