#include "MainApp.h"
#include <stdio.h>
#include <stdarg.h>
#include <algorithm>
#include "Utils/TimeUtil.h"

// features
#include "FeatureControllers/SwitchFeatureController.h"
#include "FeatureControllers/TempFeatureController.h"
#include "FeatureControllers/IRFeatureController.h"
#include "FeatureControllers/IRSensorFeatureController.h"
#include "FeatureControllers/ColorLEDViaIRDriverFeatureController.h"

//#define DEVICE_UNIQUE_ID 					"dev_sufit"
#define DEVICE_UNIQUE_ID 					"dev_tree"
//#define DEVICE_UNIQUE_ID 				"dev_sufit_dev"
//#define DEVICE_UNIQUE_ID 				"dev_temp"
#define TOPIC_DEVICE_EVENTS 			"device/events"
#define TOPIC_DEVICE_DESCRIPTION 	"device/description"
#define TOPIC_COMMAND 						"/command/"
#define TOPIC_STATE 							"/state/"
#define TOPIC_LOG			 						"/state/log"
#define TOPIC_ONLINE	 						"/state/online"
#define TOPIC_SERVICE 						"/service/"


MainApp::MainApp()
	: _deviceConfig(DEVICE_UNIQUE_ID, "WareHouse_24GHz", "bonifacy", "raspberrypi", 1883),

		_messageBus(_deviceConfig.mqttBroker, _deviceConfig.mqttBrokerPort, this, _deviceConfig.uniqueId),

		_deviceTopic(_deviceConfig.uniqueId),
		_deviceCommandTopic(_deviceTopic + TOPIC_COMMAND),
		_deviceCommandTopicSub(_deviceCommandTopic + "#"),
		_deviceStateTopic(_deviceTopic + TOPIC_STATE),
		_deviceStateLogTopic(_deviceTopic + TOPIC_LOG),
		_deviceStateOnlineTopic(_deviceTopic + TOPIC_ONLINE),
		_deviceServiceTopic(_deviceTopic + TOPIC_SERVICE),
		_deviceServiceTopicSub(_deviceServiceTopic + "#"),

		_state(DeviceState::New)
{
	_messageBus.Subscribe(_deviceCommandTopicSub.c_str());
	_messageBus.Subscribe(_deviceServiceTopicSub.c_str());
	_messageBus.SetWill(_deviceStateOnlineTopic.c_str(), "OFF", true);

	if (strcmp(_deviceConfig.uniqueId, "dev_sufit") == 0)
	{
		_pins = new ShiftRegisterPins(13, 14, 12, 20);

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
	}
	else if (strcmp(_deviceConfig.uniqueId, "dev_tree") == 0)
	{
		// choinka
		_pins = new Pins();

		_features.push_back(new SwitchFeatureController(10, this, 4, false));
		_features.push_back(new SwitchFeatureController(11, this, 5, false));
		_features.push_back(new SwitchFeatureController(15, this, 15, true));

		_features.push_back(new TempFeatureController(30, 31, this, 14));
	}
	else
	{
		// bradboard
		_pins = new Pins();

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
		_features.push_back(new ColorLEDViaIRDriverFeatureController(40, this, 16));
		_features.push_back(new ColorLEDViaIRDriverFeatureController(50, this, 5));
	}
}

MainApp::~MainApp()
{
	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
    delete feature;
	});
	_features.clear();

	if (_pins)
	{
		delete _pins;
		_pins = NULL;
	}
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
/*
void MainApp::Log(LogLevel level, const char * format, ...)
{
	va_list args;
  va_start (args, format);

	char buffer[256];
	//vsprintf(format, args);

	// http://www.cplusplus.com/reference/cstdio/vsprintf/
	//vsprintf
	va_end(args);

	//Serial.print(buffer);
}
*/
void MainApp::Log(LogLevel level, const char* msg)
{
	if (msg == NULL)
		msg = Msg();

	Serial.println(msg);
	if (level >= Info)
	{
		GetMessageBus()->Publish(_deviceStateLogTopic.c_str(), msg);
	}
}

void MainApp::Handle(const char* topic, const Buffer& payload)
{
	if (strstr(topic, _deviceCommandTopic.c_str()) == topic)
	{
		sprintf(Msg(), "[MainApp] Command arrived on topic %s", topic);
		Log(Debug);

		auto path = topic + _deviceCommandTopic.length();
		HandleDeviceMessage(path, payload);
		return;
	}

	if (strstr(topic, _deviceServiceTopic.c_str()) == topic)
	{
		sprintf(Msg(), "[MainApp] Service command arrived on topic %s", topic);
		Log(Debug);

		auto path = topic + _deviceServiceTopic.length();
		HandleServiceCommand(path, payload);
		return;
	}

	sprintf(Msg(), "[MainApp] Unsupported message arrived on topic %s", topic);
	Log(Warn);
}

void MainApp::HandleDeviceMessage(const char* path, const Buffer& payload)
{
	Log(Debug, "HandleDeviceMessage (start)");

	std::for_each(_features.begin(), _features.end(), [&payload, path](FeatureController* feature) {
		feature->TryHandle(path, payload);
	});

	Log(Debug, "HandleDeviceMessage (finish)");
	// TODO send ACK Message back to sender
}

void MainApp::HandleServiceCommand(const char* path, const Buffer& payload)
{
	Log(Debug, "[MainApp] HandleServiceCommand (start)");

	if (strcmp(path, "upgrade") == 0)
	{
		HandleUpgradeCommand(payload);
	}

	/*
	if (cmd.has_statusRequest)
	{
		HandleStatusRequest(cmd.statusRequest);
	}
	*/

	Log(Debug, "[MainApp] HandleServiceCommand (finish)");
}

void MainApp::HandleUpgradeCommand(const Buffer& payload)
{
	String url;
	payload.ToString(url);

	sprintf(Msg(), "[MainApp] Starting upgrade from: %s", url.c_str());
	Log(Warn);

	t_httpUpdate_return ret = ESPhttpUpdate.update(url);
	switch (ret)
	{
		case HTTP_UPDATE_FAILED:
			sprintf(Msg(), "[MainApp] HTTP_UPDATE_FAILD Error (%d): %s", ESPhttpUpdate.getLastError(), ESPhttpUpdate.getLastErrorString().c_str());
			Log(Error);
			break;

		case HTTP_UPDATE_NO_UPDATES:
			Log(Warn, "[MainApp] HTTP_UPDATE_NO_UPDATES");
			break;

		case HTTP_UPDATE_OK:
			Log(Warn, "[MainApp] HTTP_UPDATE_OK");
			break;
	}
}

void MainApp::HandleStatusRequest(const char* topic, const Buffer& payload)
{
	Log(Debug, "[MainApp::HandleStatusRequest] Starting");

/*
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
	*/
}

void MainApp::OnStart()
{
	Log(Info, "[MainApp] Starting...");

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
    feature->Start();
	});

	_messageBus.Publish(_deviceStateOnlineTopic.c_str(), "ON", true);

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

	Log(Info, "[MainApp] Started.");
}

void MainApp::OnStop()
{
	Log(Info, "[MainApp] Stopping...");

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

	Log(Info, "[MainApp] Stopped.");
}

void MainApp::OnLoop()
{
	if (TimeUtil::IntervalPassed(_lastMsg, 30000))
		SendHearbeat();

	std::for_each(_features.begin(), _features.end(), [](FeatureController* feature) {
		feature->Loop();
	});
}

void MainApp::SendDescription()
{
	Serial.println("[MainApp] Sending DeviceDescription...");

/*
	DeviceDescription deviceDescription = DeviceDescription_init_zero;
	strcpy(deviceDescription.device_id, _deviceConfig.uniqueId);

	int i = 0;
	std::for_each(_features.begin(), _features.end(), [&i, &deviceDescription](FeatureController* feature) {
		i += feature->Describe(deviceDescription.ports + i);
	});
	deviceDescription.ports_count = i;

	PbMessage message(DeviceDescription_fields, &deviceDescription);
	_messageBus.Publish(TOPIC_DEVICE_DESCRIPTION, &message);
	*/
}

void MainApp::SendHearbeat()
{
/*
	++_value;
	Serial.printf("[MainApp] Publish DeviceHearbeatEvent %d ...\n", _value);

	DeviceEvents deviceEvents = DeviceEvents_init_zero;
	deviceEvents.has_deviceHearbeatEvent = true;
	strcpy(deviceEvents.deviceHearbeatEvent.device_id, _deviceConfig.uniqueId);
	deviceEvents.deviceHearbeatEvent.sequence_id = _value;

	PbMessage message(DeviceEvents_fields, &deviceEvents);
	_messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
*/
}
