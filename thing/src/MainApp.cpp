#include "MainApp.h"
#include <stdio.h>
#include <algorithm>
#include "Utils/TimeUtil.h"

// features
#include "FeatureControllers/SwitchFeatureController.h"
#include "FeatureControllers/TempFeatureController.h"
#include "FeatureControllers/IRFeatureController.h"
#include "FeatureControllers/IRSensorFeatureController.h"

#define DEVICE_UNIQUE_ID 					"dev_sufit"
//#define DEVICE_UNIQUE_ID 				"dev_sufit_dev"
//#define DEVICE_UNIQUE_ID 				"dev_temp"
#define TOPIC_DEVICE_EVENTS 			"device/events"
#define TOPIC_DEVICE_DESCRIPTION 	"device/description"
#define TOPIC_COMMAND 						"/command/"
#define TOPIC_STATE 							"/state/"
#define TOPIC_SERVICE 						"/service/"


MainApp::MainApp()
	: _deviceConfig(DEVICE_UNIQUE_ID, "WareHouse_24GHz", "bonifacy", "raspberrypi", 1883),
		_pins(13, 14, 12, 20),

		_messageBus(_deviceConfig.mqttBroker, _deviceConfig.mqttBrokerPort, this, _deviceConfig.uniqueId, _serializer),

		_deviceCommandTopic(String(_deviceConfig.uniqueId) + TOPIC_COMMAND),
		_deviceCommandTopicSub(_deviceCommandTopic + "#"),
		_deviceStateTopic(String(_deviceConfig.uniqueId) + TOPIC_STATE),
		_deviceServiceTopic(String(_deviceConfig.uniqueId) + TOPIC_SERVICE),
		_deviceServiceTopicSub(_deviceServiceTopic + "#"),

		_state(DeviceState::New)
{
	_messageBus.Subscribe(_deviceCommandTopicSub.c_str());
	_messageBus.Subscribe(_deviceServiceTopicSub.c_str());

	/*
	_features.push_back(new SwitchFeatureController(10, this, 4, false));
	_features.push_back(new SwitchFeatureController(11, this, 5, false));

	_features.push_back(new TempFeatureController(30, 31, this, 0, TOPIC_DEVICE_EVENTS));

	_features.push_back(new IRFeatureController(40, this, 2));
	_features.push_back(new IRFeatureController( 50, this, 15));
	_features.push_back(new IRSensorFeatureController(41, this, 16, TOPIC_DEVICE_EVENTS));
	*/

	/*
	// sufit
	_features.push_back(new SwitchFeatureController(10, this, 20, false));
	_features.push_back(new SwitchFeatureController(11, this, 21, false));
	_features.push_back(new SwitchFeatureController(12, this, 22, false));
	_features.push_back(new SwitchFeatureController(13, this, 23, false));
	// Not used 14-15
	//_features.push_back(new SwitchFeatureController(14, this, 24, false));
	//_features.push_back(new SwitchFeatureController(15, this, 25, false));
	_features.push_back(new SwitchFeatureController(16, this, 26, false));
	_features.push_back(new SwitchFeatureController(17, this, 27, false));

	_features.push_back(new TempFeatureController(30, 31, this, 2));
	_features.push_back(new IRSensorFeatureController(41, this, 4));
	_features.push_back(new IRFeatureController(40, this, 16));
	_features.push_back(new IRFeatureController(50, this, 5));
	*/

	_features.push_back(new SwitchFeatureController(10, this, 20, false));
	_features.push_back(new SwitchFeatureController(11, this, 21, false));
	_features.push_back(new SwitchFeatureController(12, this, 22, false));
	_features.push_back(new SwitchFeatureController(13, this, 23, false));
	// Not used 14-15
	//_features.push_back(new SwitchFeatureController(14, this, 24, false));
	//_features.push_back(new SwitchFeatureController(15, this, 25, false));
	_features.push_back(new SwitchFeatureController(16, this, 4, false)); // 	_features.push_back(new SwitchFeatureController(16, this, 26, false));
	_features.push_back(new SwitchFeatureController(17, this, 5, false)); // _features.push_back(new SwitchFeatureController(17, this, 27, false));

	_features.push_back(new TempFeatureController(30, 31, this, 2));
	//_features.push_back(new IRSensorFeatureController(41, this, 4));
	//_features.push_back(new IRFeatureController(40, this, 16));
	//_features.push_back(new IRFeatureController(50, this, 5));
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
	//pinMode(LED_BUILTIN, OUTPUT);     // Initialize the BUILTIN_LED pin as an output

	SetupWifi();
}

void MainApp::Loop()
{
	_messageBus.Loop();

	if (_state == DeviceState::New)
	{
		OnStart();
		_state = DeviceState::Running;
	}

	if (_state == DeviceState::Running)
	{
		OnLoop();
	}
}

void MainApp::SetupWifi()
{
	// We start by connecting to a WiFi network
	Serial.printf("\nConnecting to network %s\n", _deviceConfig.networkName);

	WiFi.mode(WIFI_STA);
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
	if (strstr(topic, _deviceCommandTopic.c_str()) == topic)
	{
		Serial.printf("[MainApp] Command arrived on topic %s\n", topic);
		HandleDeviceMessage(topic, payload);
		return;
	}

	if (strstr(topic, _deviceServiceTopic.c_str()) == topic)
	{
		Serial.printf("[MainApp] Service command arrived on topic %s\n", topic);
		//HandleServiceCommand(cmd);
		return;
	}
	Serial.printf("[MainApp] Message arrived on topic %s\n", topic);
}

void MainApp::HandleDeviceMessage(const char* topic, const Buffer& payload)
{
	Serial.println("HandleDeviceMessage (start)");

	std::for_each(_features.begin(), _features.end(), [&payload, topic](FeatureController* feature) {
		feature->TryHandle(topic, payload);
	});

	Serial.println("HandleDeviceMessage (finish)");
	// TODO send ACK Message back to sender
}

void MainApp::HandleServiceCommand(const char* topic, const Buffer& payload)
{
	/*
	Serial.println("[MainApp] HandleServiceCommand (start)");

	if (cmd.has_upgradeFirmwareCommand)
	{
		HandleUpgradeCommand(cmd.upgradeFirmwareCommand);
	}
	if (cmd.has_statusRequest)
	{
		HandleStatusRequest(cmd.statusRequest);
	}

	Serial.println("[MainApp] HandleServiceCommand (finish)");
	*/
}

void MainApp::HandleUpgradeCommand(const UpgradeFirmwareCommand& message)
{
	t_httpUpdate_return ret = ESPhttpUpdate.update(message.program_url);
	switch (ret)
	{
		case HTTP_UPDATE_FAILED:
			Serial.printf("[MainApp] HTTP_UPDATE_FAILD Error (%d): %s\n", ESPhttpUpdate.getLastError(), ESPhttpUpdate.getLastErrorString().c_str());
			break;

		case HTTP_UPDATE_NO_UPDATES:
			Serial.println("[MainApp] HTTP_UPDATE_NO_UPDATES");
			break;

		case HTTP_UPDATE_OK:
			Serial.println("[MainApp] HTTP_UPDATE_OK");
			break;
	}
}

void MainApp::HandleStatusRequest(const DeviceStatusRequest& request)
{
	Serial.println("[MainApp::HandleStatusRequest] Starting");

	Responses responses = Responses_init_zero;
	responses.has_deviceStatusResponse = true;

	DeviceStatusResponse*	statusResponse = &responses.deviceStatusResponse;
	strcpy(statusResponse->device_id, _deviceConfig.uniqueId);
	sprintf(statusResponse->message,
		"ChipId: %d\nSketchSize: %d\nFreeSketchSpace: %d\nFreeHeap: %d\nSDK: %s\nCore: %s",
		ESP.getChipId(),
		ESP.getSketchSize(),
		ESP.getFreeSketchSpace(),
		ESP.getFreeHeap(),
		ESP.getSdkVersion(),
		ESP.getCoreVersion().c_str());

	Serial.println("[MainApp::HandleStatusRequest] Sending");
	PbMessage message(Responses_fields, &responses);
  _messageBus.Publish(request.reply_to, &message);
}

void MainApp::OnStart()
{
	Serial.println("[MainApp] Starting...");

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
    feature->Start();
	});

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

	Serial.println("[MainApp] Started.");
}

void MainApp::OnStop()
{
	Serial.println("[MainApp] Stopping...");

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
    feature->Stop();
	});

	/*
	Serial.println("Sending DeviceDisconnectedEvent");
	// Once connected, publish an announcement...
	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceDisconnectedEvent = true;
	strcpy(deviceEvents.deviceDisconnectedEvent.device_id, _deviceConfig.uniqueId);
	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
	*/

	Serial.println("[MainApp] Stopped.");
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
		i += feature->Describe(deviceDescription.ports + i);
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
