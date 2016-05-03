#include "IRTransceiverFeatureController.h"
#include "../DeviceCommands.ext.h"

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
  const char* formatLabel = EnumLabel(cmd->value.format);

  switch (cmd->value.format) {
    case IRFormat::IRFormat_NEC:
      //irsend.sendNEC(0xffffffff, 32);
      irsend.sendNEC(cmd->value.data, cmd->value.bits);
      //irsend.sendNEC(0xffffffff, 32);
      break;

    case IRFormat::IRFormat_SONY:
      irsend.sendSony(cmd->value.data, cmd->value.bits);
      break;
  }

  String msg = String("IRSendCommand for port ") + cmd->port + ", value: " + String(cmd->value.data, HEX) + ", bits: " + cmd->value.bits + ", format: " + formatLabel;
  Serial.println(msg);
}

void IRTransceiverFeatureController::Loop()
{

}
