#ifndef _DeviceConfig_h
#define _DeviceConfig_h

#include <Arduino.h>

class DeviceConfig
{
public:
  const char* uniqueId;
  const char* networkName;
  const char* networkPassword;
  const char* mqttBroker;

public:
  DeviceConfig();
  DeviceConfig(const char* uniqueId, const char* networkName, const char* networkPassword, const char* mqttBroker);
};

#endif
