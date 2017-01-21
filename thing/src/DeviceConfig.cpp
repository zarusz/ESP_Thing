#include "DeviceConfig.h"

DeviceConfig::DeviceConfig()
{
}

DeviceConfig::DeviceConfig(const char* uniqueId,
  const char* wifiName, const char* wifiPassword,
  const char* mqttHost, const int mqttPort,
  const char* mqttUser, const char* mqttPass)
{
  UniqueId = uniqueId;
  WifiName = wifiName;
  WifiPassword = wifiPassword;
  MqttHost = mqttHost;
  MqttPort = mqttPort;
  MqttUser = mqttUser;
  MqttPass = mqttPass;
}
