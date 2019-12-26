#pragma once

#include "DeviceConfig.h"
#include "Logger.h"
#include "Pins/Pins.h"
#include "Transport/MessageBus.h"
#include <Wire.h>

class DeviceContext {
public:
  virtual DeviceConfig &GetConfig() = 0;
  virtual MessageBus *GetMessageBus() = 0;
  virtual Pins &GetPins() = 0;
  virtual const String &GetStateTopic() const = 0;
  virtual Logger &GetLogger() = 0;
};
