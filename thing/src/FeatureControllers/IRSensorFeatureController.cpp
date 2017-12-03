#include "IRSensorFeatureController.h"
#include "IRFeatureController.h"

IRSensorFeatureController::IRSensorFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_IR, context),
    _irrecv(pin)
{
}

IRSensorFeatureController::~IRSensorFeatureController()
{
}

void IRSensorFeatureController::Start()
{
  _irrecv.enableIRIn(); // Start the receiver
}

void IRSensorFeatureController::Loop()
{
  if (_irrecv.decode(&_results))
  {
    auto format = GetFormat();
    auto formatLabel = EnumLabel(format);
    if (_results.decode_type == UNKNOWN)
    {
      Serial.printf("[IRSensorFeatureController] Received IR: unknown, rawlen: %d\n", _results.rawlen);
    }
    else
    {
      Serial.printf("[IRSensorFeatureController] Received IR: port %d, format: %s, bits: %d, data: %s\n", _port, formatLabel, _results.bits, String((int)_results.value, HEX).c_str());
      Publish(_results, format);
    }

    _irrecv.resume(); // Receive the next value
  }
}

void IRSensorFeatureController::Publish(decode_results& results, IRFormat format)
{
/*
  DeviceEvents events = DeviceEvents_init_zero;
  events.has_irSignalEvent = true;
  strcpy(events.irSignalEvent.device_id, _context->GetConfig().UniqueId);
  events.irSignalEvent.port = _port;
  events.irSignalEvent.signal.format = format;
  events.irSignalEvent.signal.bytes_count = 1;
  events.irSignalEvent.signal.bytes[0].bits = _results.bits;
  events.irSignalEvent.signal.bytes[0].data = _results.value;
*/
  //PbMessage message(DeviceEvents_fields, &events);
  //_context->GetMessageBus()->Publish(_topic, &message);
}

IRFormat IRSensorFeatureController::GetFormat() const
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
