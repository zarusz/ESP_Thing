#ifndef _FeatureController_h
#define _FeatureController_h

#include "../DeviceContext.h"
#include "../DeviceCommands.pb.h"

class FeatureController
{
protected:
  int port;
  DeviceContext* context;

public:
  FeatureController(int port, DeviceContext* context);
  virtual ~FeatureController();

  virtual void Handle(DeviceMessage& deviceMessage) = 0;
  virtual void Loop() = 0;
};


#endif
