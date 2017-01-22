#ifndef _DeviceConfig_h
#define _DeviceConfig_h

#include <Arduino.h>

class DeviceConfig
{
public:
  String UniqueId;
  String WifiName;
  String WifiPassword;
  String MqttHost;
  int MqttPort;
  String MqttUser;
  String MqttPass;

public:
  DeviceConfig();
  DeviceConfig(const char* uniqueId,
    const char* wifikName,
    const char* wifiPassword,
    const char* MqttHost,
    const int MqttPort,
    const char* MqttUser,
    const char* MqttPass);

  bool ReadFromFileSystem();
  bool WriteToFileSystem(String& json);
};

#endif
