#pragma once

#include "FS.h"
#include <Arduino.h>
#include <functional>

class IDeviceConfig {
public:
  String UniqueId;
  String MqttHost;
  int MqttPort;
  String MqttUser;
  String MqttPass;

public:
  virtual bool Load() = 0;
  virtual bool Save() = 0;
  virtual bool Save(String &json) = 0;
};

class DeviceConfig : public IDeviceConfig {
public:
  DeviceConfig();

  bool Load();
  bool Save();
  bool Save(String &json);

private:
  bool SaveInternal(std::function<void(File &)> operation);
};
