#ifndef _IRTransceiverFeatureController_h
#define _IRTransceiverFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class IRTransceiverFeatureController : public FeatureController
{
protected:
  IRsend irsend;

public:
  IRTransceiverFeatureController(int port, DeviceContext* context, int pin);
  virtual ~IRTransceiverFeatureController();

  virtual void Handle(DeviceMessage& deviceMessage);
  virtual void Loop();
};


#endif
