#ifndef _ColorStripOverWireFeatureController_h
#define _ColorStripOverWireFeatureController_h

#include "FeatureController.h"
#include "..\Utils\Colors.h"

class ColorStripOverWireFeatureController : public FeatureController
{
private:
  int _address;
  int _index;

  ulong _lastUpdateMs;
  uint _updateIntervalMs;

  int _h, _s, _v;
  RGB _rgb;
  bool _on;
  int _r, _g, _b;

  void SetHSV(float h, float s, float v);

public:
  ColorStripOverWireFeatureController(int port, DeviceContext* context, int address, int index);

  virtual void Start();
  virtual void Handle(const char* topic, const Buffer& payload);
  virtual void Loop();

private:
  int UpdateSlave();
};

#endif
