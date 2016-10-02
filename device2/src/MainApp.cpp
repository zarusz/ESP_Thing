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
	_features.push_back(new SwitchFeatureController(10, this, 4, false));
	_features.push_back(new SwitchFeatureController(11, this, 5, false));
	//_features.push_back(new SwitchFeatureController(12, this, 22, false));
	//_features.push_back(new SwitchFeatureController(13, this, 23, false));
	//_features.push_back(new SwitchFeatureController(14, this, 24, false));
	//_features.push_back(new SwitchFeatureController(15, this, 25, false));
	//_features.push_back(new SwitchFeatureController(16, this, 26, false));
	//_features.push_back(new SwitchFeatureController(17, this, 27, false));

	_features.push_back(new TempFeatureController(30, 31, this, 0, TOPIC_DEVICE_EVENTS));
	_features.push_back(new IRTransceiverFeatureController(40, this, 2));
	_features.push_back(new IRTransceiverFeatureController(50, this, 15));
	_features.push_back(new IRReceiverFeatureController(41, this, 16, TOPIC_DEVICE_EVENTS));
	/*
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
	*/

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

void MainApp::Handle(const char* topic, const Buffer& payload, Serializer& serializer)
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

void MainApp::HandleDeviceMessage(const DeviceMessage& message)
{
	Serial.println("HandleDeviceMessage (start)");

	std::for_each(_features.begin(), _features.end(), [&message](FeatureController* feature) {
		feature->TryHandle(message);
	});

	Serial.println("HandleDeviceMessage (finish)");
	// TODO send ACK Message back to sender
}

void MainApp::OnStart()
{
	Serial.println("Starting...");

	_messageBus.Subscribe(_deviceInTopic.c_str());

	/*
	Serial.println("Sending DeviceConnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceConnectedEvent = true;
	strcpy(deviceEvents.deviceConnectedEvent.device_id, _deviceConfig.uniqueId);
	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
	*/

	SendDescription();

	Serial.println("Started.");
}

void MainApp::OnStop()
{
	Serial.println("Stopping...");

	/*
	Serial.println("Sending DeviceDisconnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceDisconnectedEvent = true;
	strcpy(deviceEvents.deviceDisconnectedEvent.device_id, _deviceConfig.uniqueId);
	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
	*/

	Serial.println("Stopped.");
}

void MainApp::OnLoop()
{
	if (TimeUtil::IntervalPassed(_lastMsg, 30000))
	{
		SendHearbeat();
	}

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
		feature->Loop();
	});
}

void MainApp::SendDescription()
{
	Serial.println("[MainApp] Sending DeviceDescription...");

	DeviceDescription deviceDescription = DeviceDescription_init_zero;
	strcpy(deviceDescription.device_id, _deviceConfig.uniqueId);

	int i = 0;
	std::for_each(_features.begin(), _features.end(), [&i, &deviceDescription](FeatureController* feature) {
		feature->Describe(deviceDescription.ports[i]);
		i++;
	});
	deviceDescription.ports_count = i;

	PbMessage message(DeviceDescription_fields, &deviceDescription);
	_messageBus.Publish(TOPIC_DEVICE_DESCRIPTION, &message);
}

void MainApp::SendHearbeat()
{
	++_value;
	Serial.printf("[MainApp] Publish DeviceHearbeatEvent %d ...\n", _value);

	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceHearbeatEvent = true;
	strcpy(deviceEvents.deviceHearbeatEvent.device_id, _deviceConfig.uniqueId);
	deviceEvents.deviceHearbeatEvent.sequence_id = _value;

	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
}
