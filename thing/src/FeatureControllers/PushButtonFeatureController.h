#pragma once

#include "FeatureController.h"
#include "PushButton.h"
#include "SwitchFeatureController.h"

using namespace Thing::Rx;

namespace Thing { namespace FeatureControllers {

  class PushButtonFeatureController : public FeatureController,
                                      public IObserver {
  protected:
    PushButton *_pushButton;
    SwitchFeatureController *_switch;

  public:
    PushButtonFeatureController(int port,
                                DeviceContext *context,
                                PushButton *pushButton,
                                SwitchFeatureController *sw = nullptr);
    ~PushButtonFeatureController();

    virtual void Start();
    virtual void Loop();

    PushButton *GetPushButton() {
      return _pushButton;
    }

    virtual void OnNotified(void *observable, const int topic, void *argument);
  };

}} // namespace Thing::FeatureControllers