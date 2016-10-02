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

void FeatureController::Describe(DevicePort &portDesc)
{
  portDesc = (DevicePort) DevicePort_init_zero;
  portDesc.port = _port;
  portDesc.feature = _type;
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
