#pragma once

#include "../Rx/Observer.h"
#include "FeatureController.h"
#include "Values.h"

using namespace Thing::Rx;

namespace Thing { namespace FeatureControllers {

  class Led {
  protected:
    int _pin;
    uint _duration;
    String _mode;

    ulong _lastTime;
    int _lastIndex;

    bool _started;
    bool _onIsHigh = false;

  protected:
    void InitPin();

  public:
    Led(int pin = PIN_UNASSIGNED);

    virtual void Start();
    virtual void Loop();

    void SetPin(int pin);
    void SetDuration(uint duration);
    void SetMode(const char* mode);
  };

}} // namespace Thing::FeatureControllers