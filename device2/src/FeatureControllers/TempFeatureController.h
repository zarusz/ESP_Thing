#ifndef _TempFeatureController_h
#define _TempFeatureController_h

#include "FeatureController.h"
#include <DHT.h>

class TempFeatureController : public FeatureController
{
protected:
  int portForHumidity;
  int pin;
  DHT dht;
  long lastMsgMs;
  int updateIntervalMs;

public:
  TempFeatureController(int port, int portForHumidity, DeviceContext* context, int pin);
  virtual ~TempFeatureController();

  virtual void Handle(DeviceMessage& deviceMessage);
  virtual void Loop();
};

#endif
