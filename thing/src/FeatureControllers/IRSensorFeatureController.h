#ifndef _IRSensorFeatureController_h
#define _IRSensorFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class IRSensorFeatureController : public FeatureController
{
protected:
  IRrecv _irrecv;
  decode_results _results;

  IRFormat GetFormat() const;
  void Publish(decode_results& results, IRFormat format);

public:
  IRSensorFeatureController(int port, DeviceContext* context, int pin);
  virtual ~IRSensorFeatureController();

  virtual void Start();
  virtual void Loop();
};

#endif
