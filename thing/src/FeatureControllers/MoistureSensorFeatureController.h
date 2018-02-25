#pragma once

#include "FeatureController.h"

class MoistureSensorFeatureController : public FeatureController {
protected:
  int _pinSelect;
  int _pinAdc;
  ulong _lastUpdateMs;
  uint _updateIntervalMs;
  int _value;
  int _valuePublished;
  int _publishThreshold;

public:
  MoistureSensorFeatureController(int port, DeviceContext *context,
                                  int pinSelect = -1, int pinAdc = A0);
  virtual ~MoistureSensorFeatureController();

  virtual void Start();
  virtual void Loop();
};
