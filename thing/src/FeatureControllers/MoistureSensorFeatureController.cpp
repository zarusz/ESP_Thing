#include "MoistureSensorFeatureController.h"
#include "Utils/TimeUtil.h"
#include "Values.h"
#include <stdlib.h>

MoistureSensorFeatureController::MoistureSensorFeatureController(int port, DeviceContext* context, int pinSelect, int pinAdc)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_IR, context)
{
  _pinSelect = pinSelect;
  _pinAdc = pinAdc;
  _lastUpdateMs = 0;
  _updateIntervalMs = 2000;
  _value = -1;
  _valuePublished = -1;
  _publishThreshold = 5;
}

MoistureSensorFeatureController::~MoistureSensorFeatureController()
{
}

void MoistureSensorFeatureController::Start()
{
  if (_pinSelect != -1) {
    pinMode(_pinSelect, OUTPUT);
  }
}

void MoistureSensorFeatureController::Loop()
{
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  if (_pinSelect != -1) {
    digitalWrite(_pinSelect, HIGH);
    delay(10);
  }

  // https://github.com/esp8266/Arduino/blob/master/doc/reference.rst#analog-input
  // Input voltage range is 0 — 1.0V.
  int v = analogRead(_pinAdc);

  if (_pinSelect != -1) {
    digitalWrite(_pinSelect, LOW);
  }

  _value = _value != -1 ? (_value + v) / 2 : v;

  if (_valuePublished == -1 || abs(_valuePublished - _value) > _publishThreshold) {
    _valuePublished = _value;
    String payload(_value);
    FeatureController::PublishState(payload);
  }
  Serial.print("ADC = ");
  Serial.println(_value);
}
