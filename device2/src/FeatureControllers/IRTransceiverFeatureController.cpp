#include "IRTransceiverFeatureController.h"

IRTransceiverFeatureController::IRTransceiverFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, context), irsend(pin)
{
  irsend.begin();
}

IRTransceiverFeatureController::~IRTransceiverFeatureController()
{
}

void IRTransceiverFeatureController::Handle(DeviceMessage& deviceMessage)
{
  //irsend.se
  if (!deviceMessage.has_irSendCommand || deviceMessage.irSendCommand.port != port)
  {
    return;
  }
  Serial.println("IRTransceiverFeatureController:Handle");

  DeviceIRSendCommand* cmd = &deviceMessage.irSendCommand;

  String msg = String("IRSendCommand for port ") + cmd->port + " value: " + cmd->value;
  Serial.println(msg);

  irsend.sendNEC(cmd->value, 36);
}

void IRTransceiverFeatureController::Loop()
{

}
