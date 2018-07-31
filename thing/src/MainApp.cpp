#include "MainApp.h"
#include "Utils/TimeUtil.h"
#include <Wire.h>
#include <algorithm>
#include <stdarg.h>
#include <stdio.h>

// features
#include "FeatureControllers/ColorLEDViaIRDriverFeatureController.h"
#include "FeatureControllers/ColorStripFeatureController.h"
#include "FeatureControllers/ColorStripOverWireFeatureController.h"
#include "FeatureControllers/IRFeatureController.h"
#include "FeatureControllers/IRSensorFeatureController.h"
#include "FeatureControllers/MoistureSensorFeatureController.h"
#include "FeatureControllers/MotionSensorFeatureController.h"
#include "FeatureControllers/PushButtonFeatureController.h"
#include "FeatureControllers/SwitchFeatureController.h"
#include "FeatureControllers/TempFeatureController.h"
#include "FeatureControllers/Values.h"

#define DEVICE_UNIQUE_ID_SUFIT "sufit"
#define DEVICE_UNIQUE_ID_TREE "tree"
#define DEVICE_UNIQUE_ID_DEV "proto"
#define DEVICE_UNIQUE_ID_KORYTARZ "korytarz"
#define DEVICE_UNIQUE_ID_TELEWIZOR "telewizor"
#define DEVICE_UNIQUE_ID_SWITCH "switch"

#define TOPIC_BASE "dev/"
#define TOPIC_DEVICE_EVENTS "device/events"
#define TOPIC_DEVICE_DESCRIPTION "device/description"
#define TOPIC_COMMAND "/command/"
#define TOPIC_STATE "/state/"
#define TOPIC_LOG "/state/log"
#define TOPIC_ONLINE "/state/online"
#define TOPIC_SERVICE "/service/"

using namespace Thing;
using namespace Thing::FeatureControllers;

MainApp::MainApp(DeviceConfig *deviceConfig, PushButton *pushButton)
    : _deviceConfig(deviceConfig),
      _messageBus(_deviceConfig, (MessageHandler *)this),

      _deviceTopic(String(TOPIC_BASE) + _deviceConfig->UniqueId),
      _deviceCommandTopic(_deviceTopic + TOPIC_COMMAND),
      _deviceCommandTopicSub(_deviceCommandTopic + "#"),
      _deviceStateTopic(_deviceTopic + TOPIC_STATE),
      _deviceStateLogTopic(_deviceTopic + TOPIC_LOG),
      _deviceStateOnlineTopic(_deviceTopic + TOPIC_ONLINE),
      _deviceServiceTopic(_deviceTopic + TOPIC_SERVICE),
      _deviceServiceTopicSub(_deviceServiceTopic + "#"),

      _state(DeviceState::New) {
  _messageBus.Subscribe(_deviceCommandTopicSub.c_str());
  _messageBus.Subscribe(_deviceServiceTopicSub.c_str());
  _messageBus.SetWill(_deviceStateOnlineTopic.c_str(), STATE_OFF, true);

  if (_deviceConfig->UniqueId == DEVICE_UNIQUE_ID_SUFIT) {
    /*
    16 - Connected to RST (deep sleep)

    15 - Serial Data
    02 - Clock
    00 - Latch

    04 - SDA
    05 - SCL

    14 - Temp
    12 - Motion

    13 - Free
    */

    _pins = new ShiftRegisterPins(15, 2, 0, 20);
    Wire.begin(); // join i2c bus (address optional for master)

    // sufit
    _features.push_back(new SwitchFeatureController(10, this, 20, false));
    _features.push_back(new SwitchFeatureController(11, this, 21, false));
    _features.push_back(new SwitchFeatureController(12, this, 22, false));
    _features.push_back(new SwitchFeatureController(13, this, 23, false));
    _features.push_back(new SwitchFeatureController(14, this, 24, false));

    _features.push_back(
        new ColorStripOverWireFeatureController(40, this, 8, 0));
    _features.push_back(
        new ColorStripOverWireFeatureController(50, this, 8, 1));

    _features.push_back(new TempFeatureController(30, 31, this, 14));
    _features.push_back(new MotionSensorFeatureController(32, this, 12));

  } else if (_deviceConfig->UniqueId == DEVICE_UNIQUE_ID_TREE ||
             _deviceConfig->UniqueId == DEVICE_UNIQUE_ID_TELEWIZOR) {
    // choinka
    _pins = new Pins();

    /*
    16 - Connected to RST (deep sleep)
    04 - Switch 01
    05 - Switch 02
    14 - Temp
    13 - Motion
    */

    _features.push_back(new SwitchFeatureController(10, this, 4, false));
    _features.push_back(new SwitchFeatureController(11, this, 5, false));

    _features.push_back(new TempFeatureController(30, 31, this, 14));
    _features.push_back(new MotionSensorFeatureController(32, this, 12));

  } else if (_deviceConfig->UniqueId == DEVICE_UNIQUE_ID_KORYTARZ) {
    // choinka
    _pins = new Pins();

    /*
    16 - Connected to RST (deep sleep)
    14 - LED
    */

    _features.push_back(new SwitchFeatureController(10, this, 14, true));
  } else if (_deviceConfig->UniqueId.startsWith(DEVICE_UNIQUE_ID_SWITCH)) {

    // choinka
    _pins = new Pins();

    /*
    12 - Connected to RST (deep sleep)
    00 - push button, 0 - pressed, 1 - released
    */

    auto sw = new SwitchFeatureController(10, this, 12, true);
    _features.push_back(sw);
    _features.push_back(new PushButtonFeatureController(0, this, pushButton, sw));
  } else {
    // bradboard
    _pins = new Pins();
    //_pins = new ShiftRegisterPins(13, 12, 14, 20);
    Wire.begin(); // join i2c bus (address optional for master)

    _features.push_back(new MoistureSensorFeatureController(60, this, 14));
  }
}

MainApp::~MainApp() {
  for_each(_features.begin(), _features.end(), [](FeatureController *feature) { delete feature; });
  _features.clear();

  if (_pins) {
    delete _pins;
    _pins = NULL;
  }
}

bool MainApp::IsConnected() const {
  return WiFi.status() == WL_CONNECTED;
}

bool MainApp::EnsureConnected(int timeoutMs, std::function<bool()> canRetry) {
  if (IsConnected())
    return true;

  WiFi.mode(WIFI_STA);
  WiFi.begin();

  Log(Info, "WiFi: Connecting to network...");

  auto interval = TimeUtil::IntervalStart();
  while (!IsConnected() && !TimeUtil::IntervalPassed(interval, timeoutMs) && canRetry()) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("");

  if (!IsConnected()) {
    Log(Warn, "WiFi: Connection timeout!");
    return false;
  }

  sprintf(Msg(), "WiFi: Connected, IP address: %s", WiFi.localIP().toString().c_str());
  Log(Info);
  return true;
}

void MainApp::Loop() {
  if (_state == DeviceState::Sleep) {
    delay(1000);
    Log(Debug, "Sleeping");

    _state = DeviceState::Running;
  } else {
    _messageBus.Loop();

    if (_state == DeviceState::New) {
      OnStart();
      _state = DeviceState::Running;
    } else if (_state == DeviceState::Running) {
      OnLoop();
    }
  }
}

void MainApp::Log(LogLevel level, const char *msg) {
  if (msg == NULL)
    msg = Msg();

  Serial.println(msg);
  if (level >= Info && IsConnected()) {
    GetMessageBus()->Publish(_deviceStateLogTopic.c_str(), msg);
  }
}

void MainApp::Handle(const char *topic, const Buffer &payload) {
  if (strstr(topic, _deviceCommandTopic.c_str()) == topic) {
    sprintf(Msg(), "[MainApp] Command arrived on topic %s", topic);
    Log(Debug);

    auto path = topic + _deviceCommandTopic.length();
    HandleDeviceMessage(path, payload);
    return;
  }

  if (strstr(topic, _deviceServiceTopic.c_str()) == topic) {
    sprintf(Msg(), "[MainApp] Service command arrived on topic %s", topic);
    Log(Debug);

    auto path = topic + _deviceServiceTopic.length();
    HandleServiceCommand(path, payload);
    return;
  }

  sprintf(Msg(), "[MainApp] Unsupported message arrived on topic %s", topic);
  Log(Warn);
}

void MainApp::HandleDeviceMessage(const char *path, const Buffer &payload) {
  Log(Debug, "[MainApp] HandleDeviceMessage (start)");

  std::for_each(_features.begin(), _features.end(), [&payload, path](FeatureController *feature) {
    feature->TryHandle(path, payload);
  });

  Log(Debug, "[MainApp] HandleDeviceMessage (finish)");
  // TODO send ACK Message back to sender
}

void MainApp::HandleServiceCommand(const char *path, const Buffer &payload) {
  Log(Debug, "[MainApp] HandleServiceCommand (start)");

  if (strcmp(path, "upgrade") == 0) {
    HandleUpdateFirmwareCommand(payload);
  } else if (strcmp(path, "config") == 0) {
    HandleUpdateConfigCommand(payload);
  } else if (strcmp(path, "sleep") == 0) {
    HandleSleepCommand(payload);
  }

  Log(Debug, "[MainApp] HandleServiceCommand (finish)");
}

void MainApp::HandleUpdateFirmwareCommand(const Buffer &payload) {
  if (payload.Size() == 0) {
    return;
  }

  String url;
  payload.ToString(url);

  sprintf(Msg(), "[MainApp] Starting upgrade from: %s", url.c_str());
  Log(Warn);

  t_httpUpdate_return ret = ESPhttpUpdate.update(url);
  switch (ret) {
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

void MainApp::HandleUpdateConfigCommand(const Buffer &payload) {
  String config;
  payload.ToString(config);

  Log(Warn, "[MainApp] Updating config...");
  Serial.println(config.c_str());

  if (_deviceConfig->Save(config)) {
    sprintf(Msg(), "[MainApp] Config updated to: %s", config.c_str());
    Log(Warn);
  } else {
    sprintf(Msg(), "[MainApp] Could not update config to: %s", config.c_str());
    Log(Error);
  }
}

void MainApp::HandleSleepCommand(const Buffer &payload) {
  Log(Debug, "[MainApp] HandleSleepCommand (start)");

  String url;
  payload.ToString(url);
  auto sleepLevel = atoi(url.c_str());

  if (sleepLevel > 0) {
    _state = DeviceState::Sleep;

    auto value = 10;
    auto unit = 1;
    auto unitName = "seconds";

    if (sleepLevel == 2) {
      value = 1;
      unit = 60;
      unitName = "minute";
    } else if (sleepLevel == 3) {
      value = 10;
      unit = 60;
      unitName = "minutes";
    } else if (sleepLevel == 4) {
      value = 30;
      unit = 60;
      unitName = "minutes";
    }

    OnStop();

    sprintf(Msg(), "Sleep level %d, will sleep for %d %s", sleepLevel, value, unitName);
    Log(Info);

    auto sleepSeconds = value * unit;
    ESP.deepSleep(sleepSeconds * 1000000);
  }

  Log(Debug, "[MainApp] HandleSleepCommand (finish)");
}

void MainApp::HandleStatusRequest(const char *topic, const Buffer &payload) {
  Log(Debug, "MainApp::HandleStatusRequest (start)");

  /*
  Responses responses = Responses_init_zero;
  responses.has_deviceStatusResponse = true;

  DeviceStatusResponse*	statusResponse = &responses.deviceStatusResponse;
  strcpy(statusResponse->device_id, _deviceConfig.uniqueId);
  sprintf(statusResponse->message,
          "ChipId: %d\nSketchSize: %d\nFreeSketchSpace: %d\nFreeHeap: %d\nSDK:
%s\nCore: %s", ESP.getChipId(), ESP.getSketchSize(), ESP.getFreeSketchSpace(),
          ESP.getFreeHeap(),
          ESP.getSdkVersion(),
          ESP.getCoreVersion().c_str());

  Serial.println("[MainApp::HandleStatusRequest] Sending");
  PbMessage message(Responses_fields, &responses);
_messageBus.Publish(request.reply_to, &message);
  */
}

void MainApp::OnStart() {
  Log(Info, "[MainApp] Starting...");

  std::for_each(_features.begin(), _features.end(), [](FeatureController *feature) {
    feature->Start();
  });

  SendHearbeat();
  SendDescription();

  Log(Info, "[MainApp] Started.");
}

void MainApp::OnStop() {
  Log(Info, "[MainApp] Stopping...");

  std::for_each(_features.begin(), _features.end(), [](FeatureController *feature) {
    feature->Stop();
  });

  /*
  Serial.println("Sending DeviceDisconnectedEvent");
  // Once connected, publish an announcement...
  DeviceEvents deviceEvents = DeviceEvents_init_zero;
  deviceEvents.has_deviceDisconnectedEvent = true;
  strcpy(deviceEvents.deviceDisconnectedEvent.device_id,
  _deviceConfig.uniqueId); PbMessage message(DeviceEvents_fields,
  &deviceEvents); _messageBus.Publish(TOPIC_DEVICE_EVENTS, &message);
  */

  Log(Info, "[MainApp] Stopped.");
}

void MainApp::OnLoop() {
  if (TimeUtil::IntervalPassed(_lastMsg, 30000)) {
    SendHearbeat();
  }

  std::for_each(_features.begin(), _features.end(), [](FeatureController *feature) {
    feature->Loop();
  });
}

void MainApp::SendDescription() {
  Serial.println("[MainApp] Sending DeviceDescription...");

  /*
  DeviceDescription deviceDescription = DeviceDescription_init_zero;
  strcpy(deviceDescription.device_id, _deviceConfig.uniqueId);

  int i = 0;
  std::for_each(_features.begin(), _features.end(), [&i,
  &deviceDescription](FeatureController* feature) { i +=
  feature->Describe(deviceDescription.ports + i);
  });
  deviceDescription.ports_count = i;

  PbMessage message(DeviceDescription_fields, &deviceDescription);
  _messageBus.Publish(TOPIC_DEVICE_DESCRIPTION, &message);
  */
}

void MainApp::SendHearbeat() {
  _messageBus.Publish(_deviceStateOnlineTopic.c_str(), STATE_ON, true);
}
