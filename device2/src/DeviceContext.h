#ifndef _DeviceContext_h
#define _DeviceContext_h

#include "DeviceConfig.h"
#include "CommHub.h"
#include "Pins/Pins.h"

class DeviceContext
{
public:
  virtual DeviceConfig& GetConfig() = 0;
  virtual CommHub& GetCommHub() = 0;
  virtual Pins& GetPins() = 0;
};

#endif
