#pragma once

#include <Arduino.h>
#include <functional>
#include <memory>
#include <vector>

#include <ESP8266WiFi.h>
#include <ESP8266httpUpdate.h>
#include <PubSubClient.h>

#include "DeviceConfig.h"
#include "DeviceContext.h"
#include "FeatureControllers/FeatureController.h"
#include "FeatureControllers/Led.h"
#include "FeatureControllers/PushButton.h"
#include "Pins/Pins.h"
#include "Pins/ShiftRegisterPins.h"
#include "Transport/MqttMessageBus.h"

using namespace Thing::FeatureControllers;
using namespace Thing::Rx;

namespace Thing {

enum DeviceState
{
  New,
  Started,
  Running,
  Stopped,
  Sleep
};

class MainApp : public DeviceContext, public MessageHandler, public Logger {
private:
  DeviceConfig* _deviceConfig;

  String _deviceTopic;
  String _deviceCommandTopic;
  String _deviceCommandTopicSub;
  String _deviceStateTopic;
  String _deviceStateLogTopic;
  String _deviceStateOnlineTopic;
  String _deviceServiceTopic;
  String _deviceServiceTopicSub;

  MqttMessageBus _messageBus;

  std::vector<FeatureController*> _features;
  Pins* _pins;

  ulong _lastMsg = 0;
  int _value = 0;

  DeviceState _state;

public:
  MainApp(DeviceConfig* deviceConfig, PushButton* pushButton, Led* statusLed);
  virtual ~MainApp();

  bool IsConnected() const;
  bool EnsureConnected(int timeoutMs, std::function<bool()> canRetry);
  void Loop();

  virtual DeviceConfig& GetConfig() {
    return *_deviceConfig;
  }
  virtual MessageBus* GetMessageBus() {
    return &_messageBus;
  }
  virtual Pins& GetPins() {
    return *_pins;
  }
  virtual const String& GetStateTopic() const {
    return _deviceStateTopic;
  }
  virtual Logger& GetLogger() {
    return *this;
  }

  // Logger
  // virtual void Log(LogLevel level, const char* format, ...);
  virtual void Log(LogLevel level, const char* msg = 0);

  // MessageHandler
  virtual void Handle(const char* topic, const Buffer& payload);

protected:
  void ReconnectPubSub();

  // void DebugRetrievedMessage(const char* topic, const void* message);
  void HandleDeviceMessage(const char* path, const Buffer& payload);
  void HandleServiceCommand(const char* path, const Buffer& payload);
  void HandleUpdateFirmwareCommand(const Buffer& payload);
  void HandleUpdateConfigCommand(const Buffer& payload);
  void HandleSleepCommand(const Buffer& payload);
  void HandleStatusRequest(const char* topic, const Buffer& payload);

  void OnStart();
  void OnStop();
  void OnLoop();

  void SendDescription();
  void SendHearbeat();
};

} // namespace Thing