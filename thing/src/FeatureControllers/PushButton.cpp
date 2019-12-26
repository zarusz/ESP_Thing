#include "PushButton.h"
#include "../Utils/TimeUtil.h"
#include "Values.h"

using namespace Thing::FeatureControllers;

const int PushButton::TopicState = 1;
const int PushButton::TopicPush = 10;
const int PushButton::TopicLongPush = 20;

PushButton::PushButton(int pin) {
  _readInterval = 100;
  _pushDuration = 200;
  _longPushDuration = 1200;
  _pin = PIN_UNASSIGNED;

  ResetState();
  SetPin(pin);
}

void PushButton::InitPin() {
  pinMode(_pin, INPUT);
}

void PushButton::ResetState() {
  _readLast = TimeUtil::IntervalStart();
  _pushLast = 0;
  _state = false;
  _pushed = false;
  _longPushed = false;
}

void PushButton::SetPin(int pin) {
  if (_pin == pin) {
    return;
  }

  ResetState();
  _pin = pin;
  if (_started) {
    InitPin();
  }
}

void PushButton::Start() {
  _started = true;
  if (_pin != PIN_UNASSIGNED) {
    InitPin();
  }
}

void PushButton::Loop() {
  // don't read too often
  // don't read if pin unassigned
  if (!TimeUtil::IntervalPassed(_readLast, _readInterval) || _pin == PIN_UNASSIGNED) {
    return;
  }

  // off state is LOW
  bool active = digitalRead(_pin) == LOW;
  bool changed = active != _state;
  _state = active;

  if (changed) {
    _pushed = _pushLast != 0 && TimeUtil::IsIntervalPassed(_pushLast, _pushDuration) && !_pushed;
    _longPushed = _pushLast != 0 && TimeUtil::IsIntervalPassed(_pushLast, _longPushDuration) && !_longPushed;

    _pushLast = active ? TimeUtil::IntervalStart() : 0;

    Notify(PushButton::TopicState);

    if (_pushed) {
      Notify(PushButton::TopicPush);
    }
    if (_longPushed) {
      Notify(PushButton::TopicLongPush);
    }
  }
}

bool PushButton::IsOn() {
  Loop();
  return _state;
}

bool PushButton::IsPushed() {
  Loop();
  return _pushed;
}

void PushButton::ClearPushed() {
  _pushed = false;
}

bool PushButton::IsLongPushed() {
  Loop();
  return _longPushed;
}

void PushButton::ClearLongPushed() {
  _longPushed = false;
}