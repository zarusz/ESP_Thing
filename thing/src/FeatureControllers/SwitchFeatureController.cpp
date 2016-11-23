#include "SwitchFeatureController.h"

SwitchFeatureController::SwitchFeatureController(int port, DeviceContext* context, int pin, bool onIsHigh)
  : FeatureController(port, FeatureType::FeatureType_SWITCH, context),
    _pin(pin),
    _onIsHigh(onIsHigh)
{
}

void SwitchFeatureController::Start()
{
  _context->GetPins().SetMode(_pin, PinMode::Output);
  SetState(false);
}

void SwitchFeatureController::Handle(const char* topic, const Buffer& payload)
{
  auto on = payload.Data()[0] == '1';

  if (on != _on)
  {
    // if state changed
    Serial.printf("[SwitchFeatureController] Switch on port %d to %s\n", _port, on ? "on" : "off");
    SetState(on);
  }
}

void SwitchFeatureController::SetState(bool on)
{
  _on = on;
  _context->GetPins().SetValue(_pin, on ? (_onIsHigh ? true : false) : (_onIsHigh ? false : true));

  String payload = on ? "1" : "0";
  PublishState(payload);
}
