#include "IRFeatureController.h"
#include "../DeviceCommands.ext.h"

IRFeatureController::IRFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType::FeatureType_IR, context),
    _irsend(pin)
{
}

IRFeatureController::~IRFeatureController()
{
}

void IRFeatureController::Start()
{
  _irsend.begin();
}

void IRFeatureController::Handle(const DeviceMessage& deviceMessage)
{
  //irsend.se
  if (!deviceMessage.has_irSendCommand || deviceMessage.irSendCommand.port != _port)
  {
    return;
  }
  Serial.println("IRTransceiverFeatureController:Handle");

  const DeviceIRSendCommand* cmd = &deviceMessage.irSendCommand;
  const char* formatLabel = EnumLabel(cmd->value.format);

  switch (cmd->value.format) {
    case IRFormat::IRFormat_NEC:
      //irsend.sendNEC(0xffffffff, 32);
      _irsend.sendNEC(cmd->value.data, cmd->value.bits);
      //irsend.sendNEC(0xffffffff, 32);
      break;

    case IRFormat::IRFormat_SONY:
      _irsend.sendSony(cmd->value.data, cmd->value.bits);
      break;
  }

  String msg = String("IRSendCommand for port ") + cmd->port + ", value: " + String(cmd->value.data, HEX) + ", bits: " + cmd->value.bits + ", format: " + formatLabel;
  Serial.println(msg);
}
