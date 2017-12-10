#ifndef _MotionSensorFeatureController_h
#define _MotionSensorFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class MotionSensorFeatureController : public FeatureController
{
protected:
  int _pin;

  ulong _readLast;
  uint _readInterval;

  ulong _windowStart;
  uint _windowDuration;
  bool _windowState;

  volatile bool _state;

public:
  MotionSensorFeatureController(int port, DeviceContext* context, int pin);
  virtual ~MotionSensorFeatureController();

  virtual void Start();
  virtual void Loop();
};

#endif
