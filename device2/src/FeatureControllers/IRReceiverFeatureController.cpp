#include "IRReceiverFeatureController.h"

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
    Serial.println(String("Recived IR bits: ") + results.bits + ", value: " + results.value);
    irrecv.resume(); // Receive the next value

    DeviceEvents events = DeviceEvents_init_zero;
    events.has_irReceivedEvent = true;
    strcpy(events.irReceivedEvent.device_id, context->GetConfig().uniqueId);
    events.irReceivedEvent.port = port;
    events.irReceivedEvent.value = results.value;

    context->GetCommHub().PublishMessage(EVENTS_TOPIC, DeviceEvents_fields, &events);
  }
}
