#ifndef _ColorStripFeatureController_h
#define _ColorStripFeatureController_h

#include "FeatureController.h"
#include "..\Utils\Colors.h"

class ColorStripFeatureController : public FeatureController
{
private:
  int _pinR, _pinG, _pinB;
  int _h, _s, _v;
  RGB _rgb;
  bool _on;

  void SetHSV(float h, float s, float v);

public:
  ColorStripFeatureController(int port, DeviceContext* context, int pinR, int pinG, int pinB);

  virtual void Start();
  virtual void Handle(const char* topic, const Buffer& payload);
};

#endif
