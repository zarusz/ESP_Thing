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
}

void IRTransceiverFeatureController::Loop()
{

}
