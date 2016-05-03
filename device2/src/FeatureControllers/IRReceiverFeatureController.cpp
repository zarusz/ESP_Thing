#include "IRReceiverFeatureController.h"
#include "../DeviceCommands.ext.h"

IRReceiverFeatureController::IRReceiverFeatureController(int port, DeviceContext* context, int pin)
  : FeatureController(port, context), irrecv(pin)
{
  irrecv.enableIRIn(); // Start the receiver
}

IRReceiverFeatureController::~IRReceiverFeatureController()
{
}

void IRReceiverFeatureController::Handle(DeviceMessage& deviceMessage)
{
  //irsend.se
}

void IRReceiverFeatureController::Loop()
{
  if (irrecv.decode(&results))
  {
    IRFormat format = GetFormat();
    String formatLabel = EnumLabel(format);
    String msg = String("Recived IR, value: ") + String(results.value, HEX) + ", bits: " + results.bits + ", format: " + formatLabel;
    Serial.println(msg);
    if (results.decode_type == UNKNOWN)
    {
      msg = String("Recived IR - Unknown. Rawlen: ") + results.rawlen;
      Serial.println(msg);
    }
    else
    {
      DeviceEvents events = DeviceEvents_init_zero;
      events.has_irReceivedEvent = true;
      strcpy(events.irReceivedEvent.device_id, context->GetConfig().uniqueId);
      events.irReceivedEvent.port = port;
      events.irReceivedEvent.value.bits = results.bits;
      events.irReceivedEvent.value.data = results.value;
      events.irReceivedEvent.value.format = format;

      context->GetCommHub().PublishMessage(EVENTS_TOPIC, DeviceEvents_fields, &events);
    }

    irrecv.resume(); // Receive the next value
  }
}

IRFormat IRReceiverFeatureController::GetFormat() const
{
  switch (results.decode_type)
  {
    case NEC:
      return IRFormat::IRFormat_NEC;
    case SONY:
      return IRFormat::IRFormat_SONY;
  }
  return IRFormat::IRFormat_Unknown;
}
