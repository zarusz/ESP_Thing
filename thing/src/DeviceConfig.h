#ifndef _DeviceConfig_h
#define _DeviceConfig_h

#include <Arduino.h>

class DeviceConfig
{
public:
  const char* UniqueId;
  const char* WifiName;
  const char* WifiPassword;
  const char* MqttHost;
  int MqttPort;
  const char* MqttUser;
  const char* MqttPass;

public:
  DeviceConfig();
  DeviceConfig(const char* uniqueId,
    const char* wifikName,
    const char* wifiPassword,
    const char* MqttHost,
    const int MqttPort,
    const char* MqttUser,
    const char* MqttPass);
};

#endif
