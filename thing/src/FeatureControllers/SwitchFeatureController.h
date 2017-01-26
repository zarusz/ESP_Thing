#ifndef _SwitchFeatureController_h
#define _SwitchFeatureController_h

#include "FeatureController.h"

class SwitchFeatureController : public FeatureController
{
protected:
  int _pin;
  bool _onIsHigh;
  bool _on;

  ulong _lastUpdateMs;
  uint _updateIntervalMs;

  void SetState(bool on);
  void PublishState();

public:
  SwitchFeatureController(int port, DeviceContext* context, int pin, bool onIsHigh);

  virtual void Start();
  virtual void Handle(const char* topic, const Buffer& payload);
  virtual void Loop();
};

#endif
