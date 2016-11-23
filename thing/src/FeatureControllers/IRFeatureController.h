#ifndef _IRFeatureController_h
#define _IRFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class IRFeatureController : public FeatureController
{
protected:
  IRsend _irsend;

public:
  IRFeatureController(int port, DeviceContext* context, int pin);
  virtual ~IRFeatureController();

  virtual void Start();
  virtual void Handle(const char* topic, const Buffer& payload);
};


#endif
