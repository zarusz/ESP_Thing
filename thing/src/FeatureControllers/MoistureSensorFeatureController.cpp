#include "MoistureSensorFeatureController.h"
#include "Utils/TimeUtil.h"
#include "Values.h"

MoistureSensorFeatureController::MoistureSensorFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_IR, context)
{
  _pin = pin;
  _lastUpdateMs = 0;
  _updateIntervalMs = 2000;
  _lastActive = false;
}

MoistureSensorFeatureController::~MoistureSensorFeatureController()
{
}

void MoistureSensorFeatureController::Start()
{
}

void MoistureSensorFeatureController::Loop()
{
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  // https://github.com/esp8266/Arduino/blob/master/doc/reference.rst#analog-input
  // Input voltage range is 0 — 1.0V.
  int v = analogRead(A0);

  auto active = digitalRead(_pin) == HIGH;
  if (active != _lastActive) {
      _lastActive = active;

      String payload = active ? STATE_ON : STATE_OFF;
      FeatureController::PublishState(payload);
  }
}
