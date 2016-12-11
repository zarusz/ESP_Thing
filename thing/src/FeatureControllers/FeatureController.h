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
  Logger* _logger;

public:
  FeatureController(int port, FeatureType type, DeviceContext* context);
  virtual ~FeatureController();

  virtual void Start();
  virtual void Stop();

  virtual uint Describe(DevicePort* ports);
  virtual void Loop();

  virtual bool TryHandle(const char* path, const Buffer& payload);
  virtual bool CanHandle(const char* path, const Buffer& payload);

protected:
  virtual void Handle(const char* path, const Buffer& payload);
  virtual void PublishState(const String& payload, int port = 0);
};


#endif
