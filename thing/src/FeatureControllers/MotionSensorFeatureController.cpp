#include "MotionSensorFeatureController.h"
#include "Utils/TimeUtil.h"
#include "Values.h"

MotionSensorFeatureController::MotionSensorFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType::SENSOR_MOTION, context)
{
  _pin = pin;

  _readLast = TimeUtil::IntervalStart();
  _readInterval = 200;
  _state = false;

  _windowStart = 0;
  _windowDuration = 1100;
  _windowState = false;
}

MotionSensorFeatureController::~MotionSensorFeatureController()
{
}

void MotionSensorFeatureController::Start()
{
  pinMode(_pin, INPUT);
}

void MotionSensorFeatureController::Loop()
{
  if (!TimeUtil::IntervalPassed(_readLast, _readInterval))
    return;

  bool active = digitalRead(_pin) == HIGH;
  if (active != _windowState)
  {
    _windowState = active;
    _windowStart = active ? TimeUtil::IntervalStart() : 0;
  }

  active = _windowStart != 0 && TimeUtil::IsIntervalPassed(_windowStart, _windowDuration);

  if (active != _state)
  {
    _state = active;
    FeatureController::PublishState(_state ? STATE_ON : STATE_OFF);
  }
}
