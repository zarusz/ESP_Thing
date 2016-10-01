#ifndef _FeatureController_h
#define _FeatureController_h

#include "../DeviceContext.h"
#include "../DeviceCommands.pb.h"

class FeatureController
{
protected:
  int _port;
  FeatureType _type;
  DeviceContext* _context;

public:
  FeatureController(int port, FeatureType type, DeviceContext* context);
  virtual ~FeatureController();

  virtual void Describe(DevicePort &portDesc);
  virtual void Loop();

  virtual bool TryHandle(const DeviceMessage& deviceMessage);
  virtual bool CanHandle(const DeviceMessage& deviceMessage);

protected:
  virtual void Handle(const DeviceMessage& deviceMessage);
};


#endif
