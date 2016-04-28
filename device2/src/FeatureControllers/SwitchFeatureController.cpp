#include "SwitchFeatureController.h"


#define MUX_EN 16
#define MUX_SIG 12
#define MUX_A0 13
#define MUX_A1 14
SwitchFeatureController::SwitchFeatureController(int port, DeviceContext* context, int pin, bool onIsHigh)
  : FeatureController(port, context)
{
  this->pin = pin;
  this->onIsHigh = onIsHigh;

  context->GetPins().SetMode(pin, PinMode::Output);
  //pinMode(pin, OUTPUT);
  SetState(false);
}

void SwitchFeatureController::Handle(DeviceMessage& deviceMessage)
{
  if (!deviceMessage.has_switchCommand || deviceMessage.switchCommand.port != port)
  {
    return;
  }
  Serial.println("SwitchFeatureController:Handle");

  DeviceSwitchCommand* cmd = &deviceMessage.switchCommand;

  String msg = String("SwitchCommand for port ") + cmd->port + " with " + (cmd->on ? "turn on" : "turn off");
  Serial.println(msg);
  SetState(cmd->on);
}

void SwitchFeatureController::SetState(bool on)
{
  //auto onState = on ? (onIsHigh ? HIGH : LOW) : (onIsHigh ? LOW : HIGH);
  //digitalWrite(pin, onState);
  context->GetPins().SetValue(pin, on ? (onIsHigh ? true : false) : (onIsHigh ? false : true));
}

void SwitchFeatureController::Loop()
{
}
