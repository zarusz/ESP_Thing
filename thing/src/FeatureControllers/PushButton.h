#pragma once

#include "../Rx/Observer.h"
#include "FeatureController.h"
#include "Values.h"

using namespace Thing::Rx;

namespace Thing { namespace FeatureControllers {

  class PushButton : public Observable {
  protected:
    int _pin;

    ulong _readLast;
    uint _readInterval;

    ulong _pushLast;

    uint _pushDuration;
    uint _longPushDuration;

    bool _state;
    bool _pushed;
    bool _longPushed;

    bool _started;

  protected:
    virtual void InitPin();
    virtual void ResetState();

  public:
    PushButton(int pin = PIN_UNASSIGNED);

    virtual void Start();
    virtual void Loop();

    void SetPin(int pin);

    bool IsOn();
    bool IsPushed();
    void ClearPushed();
    bool IsLongPushed();
    void ClearLongPushed();

  public:
    static const uint TopicState;
    static const uint TopicPush;
    static const uint TopicLongPush;
  };

}} // namespace Thing::FeatureControllers