#include "FeatureController.h"

FeatureController::FeatureController(int port, DeviceContext* context)
{
    this->port = port;
    this->context = context;
}

FeatureController::~FeatureController()
{
}
