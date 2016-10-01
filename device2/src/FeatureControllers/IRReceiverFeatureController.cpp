#include "IRReceiverFeatureController.h"
#include "../DeviceCommands.ext.h"

IRReceiverFeatureController::IRReceiverFeatureController(int port, DeviceContext* context, int pin, const char* topic)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_IR, context),
    _irrecv(pin)
{
  _topic = topic;
  _irrecv.enableIRIn(); // Start the receiver
}

IRReceiverFeatureController::~IRReceiverFeatureController()
{
}

void IRReceiverFeatureController::Loop()
{
  if (_irrecv.decode(&_results))
  {
    IRFormat format = GetFormat();
    String formatLabel = EnumLabel(format);
    String msg = String("Recived IR, value: ") + String(_results.value, HEX) + ", bits: " + _results.bits + ", format: " + formatLabel;
    Serial.println(msg);
    if (_results.decode_type == UNKNOWN)
    {
      msg = String("Recived IR - Unknown. Rawlen: ") + _results.rawlen;
      Serial.println(msg);
    }
    else
    {
      Publish(_results, format);
    }

    _irrecv.resume(); // Receive the next value
  }
}

void IRReceiverFeatureController::Publish(decode_results& results, IRFormat format)
{
  DeviceEvents events = DeviceEvents_init_zero;
  events.has_irReceivedEvent = true;
  strcpy(events.irReceivedEvent.device_id, _context->GetConfig().uniqueId);
  events.irReceivedEvent.port = _port;
  events.irReceivedEvent.value.bits = _results.bits;
  events.irReceivedEvent.value.data = _results.value;
  events.irReceivedEvent.value.format = format;

  PbMessage message(DeviceEvents_fields, &events);
  _context->GetMessageBus()->Publish(_topic, &message);
}

IRFormat IRReceiverFeatureController::GetFormat() const
{
  switch (_results.decode_type)
  {
    case NEC:
      return IRFormat::IRFormat_NEC;
    case SONY:
      return IRFormat::IRFormat_SONY;
  }
  return IRFormat::IRFormat_Unknown;
}
