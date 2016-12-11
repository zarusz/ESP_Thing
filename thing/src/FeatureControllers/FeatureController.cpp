#include "FeatureController.h"

FeatureController::FeatureController(int port, FeatureType type, DeviceContext* context)
{
  _port = port;
  _type = type;
  _context = context;
  _logger = &context->GetLogger();
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

bool FeatureController::TryHandle(const char* path, const Buffer& payload)
{
  if (CanHandle(path, payload))
  {
    Handle(path, payload);
    return true;
  }
  return false;
}

bool FeatureController::CanHandle(const char* path, const Buffer& payload)
{
  char portStr[6];
  itoa(_port, portStr, 10);
  return strcmp(portStr, path) == 0;
}

void FeatureController::Handle(const char* topic, const Buffer& payload)
{

}

void FeatureController::Loop()
{
}

void FeatureController::PublishState(const String& payload, int port)
{
  if (port == 0)
    port = _port;

  char topic[64];
  snprintf(topic, sizeof(topic), "%s%d", _context->GetStateTopic().c_str(), port);
  _context->GetMessageBus()->Publish(topic, payload);
}
