#ifndef _DeviceContext_h
#define _DeviceContext_h

#include "DeviceConfig.h"
#include "Transport/MessageBus.h"
#include "Transport/PbSerializer.h"
#include "Pins/Pins.h"

class DeviceContext
{
public:
  virtual DeviceConfig& GetConfig() = 0;
  virtual MessageBus* GetMessageBus() = 0;
  virtual Pins& GetPins() = 0;
};

#endif
