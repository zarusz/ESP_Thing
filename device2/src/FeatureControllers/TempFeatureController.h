#ifndef _TempFeatureController_h
#define _TempFeatureController_h

#include "FeatureController.h"
#include <DHT.h>

class TempFeatureController : public FeatureController
{
protected:
  const char* _topic;
  int _portForHumidity;
  int _pin;
  DHT _dht;

  bool _lastTemp;
  ulong _lastUpdateMs;
  uint _updateIntervalMs;

public:
  TempFeatureController(int port, int portForHumidity, DeviceContext* context, int pin, const char* topic);
  virtual ~TempFeatureController();

  virtual void Loop();
};

#endif
