#include "PushButton.h"
#include "Utils/TimeUtil.h"
#include "Values.h"

using namespace Thing::FeatureControllers;

const uint PushButton::TopicState = 1;
const uint PushButton::TopicPush = 10;
const uint PushButton::TopicLongPush = 20;

PushButton::PushButton(int pin) {
  _pin = pin;

  _readLast = TimeUtil::IntervalStart();
  _readInterval = 200;

  _pushDuration = 200;
  _longPushDuration = 1200;

  _pushLast = 0;

  _state = false;
  _pushed = false;
  _longPushed = false;
}

void PushButton::Start() {
  pinMode(_pin, INPUT);
}

void PushButton::Loop() {
  if (!TimeUtil::IntervalPassed(_readLast, _readInterval))
    return;

  // off state is LOW
  bool active = digitalRead(_pin) == LOW;
  bool changed = active != _state;
  _state = active;

  if (changed) {
    _pushed = _pushLast != 0 &&
              TimeUtil::IsIntervalPassed(_pushLast, _pushDuration) && !_pushed;
    _longPushed = _pushLast != 0 &&
                  TimeUtil::IsIntervalPassed(_pushLast, _longPushDuration) &&
                  !_longPushed;

    _pushLast = active ? TimeUtil::IntervalStart() : 0;

    Notify(PushButton::TopicState);

    if (_pushed)
      Notify(PushButton::TopicPush);

    if (_longPushed)
      Notify(PushButton::TopicLongPush);
  }
}

bool PushButton::IsOn() {
  Loop();
  return _state;
}

bool PushButton::IsPushed() {
  Loop();
  auto p = _pushed;
  _pushed = false;
  return p;
}

bool PushButton::IsLongPushed() {
  Loop();
  auto p = _longPushed;
  _longPushed = false;
  return p;
}
