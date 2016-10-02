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
  //pinMode(pin, OUTPUT);
  SetState(false);
}

void SwitchFeatureController::Handle(const DeviceMessage& deviceMessage)
{
  if (!deviceMessage.has_switchCommand || deviceMessage.switchCommand.port != _port)
  {
    return;
  }
  Serial.println("SwitchFeatureController:Handle");

  const DeviceSwitchCommand* cmd = &deviceMessage.switchCommand;

  String msg = String("SwitchCommand for port ") + cmd->port + " with " + (cmd->on ? "turn on" : "turn off");
  Serial.println(msg);
  SetState(cmd->on);
}

void SwitchFeatureController::SetState(bool on)
{
  //auto onState = on ? (onIsHigh ? HIGH : LOW) : (onIsHigh ? LOW : HIGH);
  //digitalWrite(pin, onState);
  _context->GetPins().SetValue(_pin, on ? (_onIsHigh ? true : false) : (_onIsHigh ? false : true));
}
