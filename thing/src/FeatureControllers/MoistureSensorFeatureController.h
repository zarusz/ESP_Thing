#ifndef _MoistureSensorFeatureController_h
#define _MoistureSensorFeatureController_h

#include "FeatureController.h"

class MoistureSensorFeatureController : public FeatureController
{
protected:
  int _pin;
  ulong _lastUpdateMs;
  uint _updateIntervalMs;
  bool _lastActive;

public:
  MoistureSensorFeatureController(int port, DeviceContext* context, int pin);
  virtual ~MoistureSensorFeatureController();

  virtual void Start();
  virtual void Loop();
};

#endif
