#include "FeatureController.h"

FeatureController::FeatureController(int port, FeatureType type, DeviceContext* context)
{
  _port = port;
  _type = type;
  _context = context;
}

FeatureController::~FeatureController()
{
}

void FeatureController::Start()
{
}

void FeatureController::Stop()
{
}

uint FeatureController::Describe(DevicePort* ports)
{
  ports[0] = (DevicePort) DevicePort_init_zero;
  ports[0].port = _port;
  ports[0].feature = _type;
  return 1;
}

bool FeatureController::TryHandle(const DeviceMessage& deviceMessage)
{
  if (CanHandle(deviceMessage))
  {
    Handle(deviceMessage);
    return true;
  }
  return false;
}

bool FeatureController::CanHandle(const DeviceMessage& deviceMessage)
{
  return true;
}

void FeatureController::Handle(const DeviceMessage &deviceMessage)
{
}

void FeatureController::Loop()
{
}
