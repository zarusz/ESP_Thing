#ifndef _IRReceiverFeatureController_h
#define _IRReceiverFeatureController_h

#include "FeatureController.h"
#include <IRremoteESP8266.h>

class IRReceiverFeatureController : public FeatureController
{
protected:
  const char* _topic;
  IRrecv _irrecv;
  decode_results _results;

  IRFormat GetFormat() const;
  void Publish(decode_results& results, IRFormat format);

public:
  IRReceiverFeatureController(int port, DeviceContext* context, int pin, const char* topic);
  virtual ~IRReceiverFeatureController();

  virtual void Loop();
};

#endif
