#pragma once

#include "DeviceConfig.h"
using namespace std;

class DeviceConfigManager {
private:
  bool _deviceConfigLoaded;
  DeviceConfig *_deviceConfig;

public:
  DeviceConfigManager(DeviceConfig *deviceConfig);

  bool EnsureConfigLoaded();
  bool EnterConfigMode();
};
