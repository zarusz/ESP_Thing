#ifndef _IRReceiverFeatureController_h
#define _IRReceiverFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class IRReceiverFeatureController : public FeatureController
{
protected:
  IRrecv irrecv;
  decode_results results;

public:
  IRReceiverFeatureController(int port, DeviceContext* context, int pin);
  virtual ~IRReceiverFeatureController();

  virtual void Handle(DeviceMessage& deviceMessage);
  virtual void Loop();
};


#endif
