#ifndef _FeatureController_h
#define _FeatureController_h

#include "../CommHub.h"
#include "../DeviceCommands.pb.h"

class FeatureController
{
protected:
  int port;

public:
  FeatureController(int port);
  virtual ~FeatureController();

  virtual void Handle(CommHub& commHub, DeviceMessage& deviceMessage) = 0;
  virtual void Loop(CommHub& commHub) = 0;
};


#endif
