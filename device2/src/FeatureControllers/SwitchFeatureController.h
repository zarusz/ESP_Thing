#ifndef _SwitchFeatureController_h
#define _SwitchFeatureController_h

#include "FeatureController.h"

class SwitchFeatureController : public FeatureController
{
protected:
  int pin;
  bool onIsHigh;

  void SetState(bool on);

public:
  SwitchFeatureController(int port, int pin, bool onIsHigh);

  virtual void Handle(CommHub& commHub, DeviceMessage& deviceMessage);
  virtual void Loop(CommHub& commHub);
};

#endif
