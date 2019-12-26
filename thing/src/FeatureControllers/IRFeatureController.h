#pragma once

#include "FeatureController.h"
#include <IRremoteESP8266.h>
#include <IRsend.h>

class IRFeatureController : public FeatureController {
protected:
  IRsend _irsend;

public:
  IRFeatureController(int port, DeviceContext *context, int pin);
  virtual ~IRFeatureController();

  virtual void Start();
  virtual void Handle(const char *topic, const Buffer &payload);
};

const char *EnumLabel(IRFormat format);
