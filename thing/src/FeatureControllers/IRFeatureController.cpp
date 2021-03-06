#include "IRFeatureController.h"

IRFeatureController::IRFeatureController(int port, DeviceContext *context,
                                         int pin)
    : FeatureController(port, FeatureType::IR, context), _irsend(pin) {
}

IRFeatureController::~IRFeatureController() {
}

void IRFeatureController::Start() {
  _irsend.begin();
}

void IRFeatureController::Handle(const char *topic, const Buffer &payload) {
  /*
    if (!deviceMessage.has_irCommand || deviceMessage.irCommand.port != _port)
    {
      return;
    }
    Serial.println("[IRFeatureController] Handle");

    const DeviceIRCommand* cmd = &deviceMessage.irCommand;
    const char* formatLabel = EnumLabel(cmd->signal.format);
    for (auto i = 0; i < cmd->signal.bytes_count; i++)
    {
      auto s = cmd->signal.bytes + i;
      switch (cmd->signal.format) {
        case IRFormat::IRFormat_NEC:
          _irsend.sendNEC(s->data, s->bits);
          break;

        case IRFormat::IRFormat_SONY:
          _irsend.sendSony(s->data, s->bits);
          break;
      }
      Serial.printf("[IRFeatureController] Sending IR: port %d, format: %s,
    bits: %d, data: %s\n", cmd->port, formatLabel, s->bits, String(s->data,
    HEX).c_str());
    }
  */
}

const char *EnumLabel(IRFormat format) {
  switch (format) {
  case IRFormat_NEC:
    return "NEC";
  case IRFormat_SONY:
    return "SONY";
  default:
    return "Unknown";
  }
}
