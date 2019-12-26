#include "Led.h"
#include "../Utils/TimeUtil.h"
#include "Values.h"

using namespace Thing::FeatureControllers;

Led::Led(int pin) {
  _pin = PIN_UNASSIGNED;

  SetPin(pin);
  SetDuration(250);
  SetMode("0");
}

void Led::Start() {
  _started = true;
  if (_pin != PIN_UNASSIGNED) {
    InitPin();
  }
}

void Led::Loop() {
  if (_pin == PIN_UNASSIGNED) {
    return;
  }

  bool write = false;

  if (_lastIndex == -1) {
    _lastTime = TimeUtil::IntervalStart();
    _lastIndex = 0;
    write = true;
  }

  if (TimeUtil::IsIntervalPassed(_lastTime, _duration)) {
    _lastTime += _duration;
    _lastIndex = (_lastIndex + 1) % _mode.length();
    write = true;
  }

  if (write) {
    auto on = _mode.charAt(_lastIndex) == '1';
    if (_onIsHigh) {
      digitalWrite(_pin, on ? HIGH : LOW);
    } else {
      digitalWrite(_pin, on ? LOW : HIGH);
    }
  }
}

void Led::InitPin() {
  pinMode(_pin, OUTPUT);
}

void Led::SetPin(int pin) {
  if (_pin == pin) {
    return;
  }

  _pin = pin;
  if (_pin != PIN_UNASSIGNED && _started) {
    InitPin();
  }
}

void Led::SetDuration(uint duration) {
  _duration = duration;
}

void Led::SetMode(const char *mode) {
  _mode = mode;
  _lastIndex = -1;
}
