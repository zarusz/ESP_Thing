#include "SwitchFeatureController.h"
#include "Utils/TimeUtil.h"
#include "Values.h"

SwitchFeatureController::SwitchFeatureController(int port,
                                                 DeviceContext *context,
                                                 int pin, bool onIsHigh)
    : FeatureController(port, FeatureType::SWITCH, context), _pin(pin),
      _onIsHigh(onIsHigh) {
  _lastUpdateMs = 0;
  _updateIntervalMs = 10000;
}

void SwitchFeatureController::Start() {
  _context->GetPins().SetMode(_pin, PinMode::Output);
  SetOn(false);
}

void SwitchFeatureController::Stop() {
  SetOn(false);
}

void SwitchFeatureController::Handle(const char *topic, const Buffer &payload) {
  String str;
  payload.ToString(str);

  auto on = str == STATE_ON;
  auto off = str == STATE_OFF;
  if (!on && !off) {
    sprintf(_logger->Msg(),
            "[SwitchFeatureController] Bad value '%s' (espected %s or %s)",
            str.c_str(), STATE_ON, STATE_OFF);
    _logger->Log(Warn);
    return;
  }

  if (on != _on) {
    SetOn(on);
  }
}

void SwitchFeatureController::Loop() {
  if (_updateIntervalMs == 0 || !_context->GetMessageBus()->IsConnected() ||
      !TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  _updateIntervalMs = 0;
  PublishState();
}

void SwitchFeatureController::PublishState() {
  String payload = _on ? STATE_ON : STATE_OFF;
  FeatureController::PublishState(payload);
}

void SwitchFeatureController::SetOn(bool on) {
  sprintf(_logger->Msg(), "[SwitchFeatureController] Switch on port %d to %s",
          _port, on ? "on" : "off");
  _logger->Log(Debug);

  _on = on;
  _context->GetPins().SetValue(_pin, on ? (_onIsHigh ? true : false)
                                        : (_onIsHigh ? false : true));
  PublishState();
}

void SwitchFeatureController::Toggle() {
  SetOn(!_on);
}