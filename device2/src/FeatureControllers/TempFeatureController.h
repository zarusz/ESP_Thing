#ifndef _TempFeatureController_h
#define _TempFeatureController_h

#include "FeatureController.h"
#include <DHT.h>

class TempFeatureController : public FeatureController
{
protected:
  int pin;
  DHT dht;
  long lastMsgMs;
  int updateIntervalMs;

public:
  TempFeatureController(int port, DeviceContext* context, int pin);
  virtual ~TempFeatureController();

  virtual void Handle(DeviceMessage& deviceMessage);
  virtual void Loop();
};

#endif
