#include "PushButtonFeatureController.h"
#include "../Utils/TimeUtil.h"
#include "Values.h"

using namespace Thing::FeatureControllers;
using namespace Thing::Rx;

PushButtonFeatureController::PushButtonFeatureController(int port,
                                                         DeviceContext *context,
                                                         PushButton *pushButton,
                                                         SwitchFeatureController *sw)
    : FeatureController(port, FeatureType::SENSOR_MOTION, context) {

  _switch = sw;
  _pushButton = pushButton;
  _pushButton->Subscribe(this);
}

PushButtonFeatureController::~PushButtonFeatureController() {
  _pushButton->Unsubscribe(this);
}

void PushButtonFeatureController::Start() {
  _pushButton->Start();
}

void PushButtonFeatureController::Loop() {
  _pushButton->Loop();
}

void PushButtonFeatureController::OnNotified(void *observable, const int topic,
                                             void *argument) {
  if (topic == PushButton::TopicState) {
    if (_switch != nullptr && _pushButton->IsOn()) {
      _switch->Toggle();
    }
    FeatureController::PublishState(_pushButton->IsOn() ? STATE_ON : STATE_OFF);
  }
}
