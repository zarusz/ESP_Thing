#include "MotionSensorFeatureController.h"
#include "Utils/TimeUtil.h"
#include "Values.h"

MotionSensorFeatureController::MotionSensorFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_IR, context)
{
  _pin = pin;
  _lastUpdateMs = 0;
  _updateIntervalMs = 2000;
  _lastActive = false;
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
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  auto active = digitalRead(_pin) == HIGH;
  if (active != _lastActive) {
      _lastActive = active;

      String payload = active ? STATE_ON : STATE_OFF;
      FeatureController::PublishState(payload);
  }
}
