#include "DeviceConfig.h"

DeviceConfig::DeviceConfig()
{
}

DeviceConfig::DeviceConfig(const char* uniqueId, const char* networkName, const char* networkPassword, const char* mqttBroker, const int mqttBrokerPort)
{
  this->uniqueId = uniqueId;
  this->networkName = networkName;
  this->networkPassword = networkPassword;
  this->mqttBroker = mqttBroker;
  this->mqttBrokerPort = mqttBrokerPort;
}
