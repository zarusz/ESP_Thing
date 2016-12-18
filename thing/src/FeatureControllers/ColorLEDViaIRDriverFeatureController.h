#ifndef _ColorLEDViaIRDriverFeatureController_h
#define _ColorLEDViaIRDriverFeatureController_h

#include "IRFeatureController.h"

class ColorLEDViaIRDriverFeatureController : public IRFeatureController
{
public:
  ColorLEDViaIRDriverFeatureController(int port, DeviceContext* context, int pin);

  virtual void Handle(const char* topic, const Buffer& payload);

protected:
  void SendSignal(int value, const char* command);
};

#endif
