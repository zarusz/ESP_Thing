#pragma once
#include "IObserver.h"

namespace Thing { namespace Rx {

  class IObservable {
  public:
    virtual void Subscribe(IObserver *observer) = 0;
    virtual void Unsubscribe(IObserver *observer) = 0;
    virtual void Notify(const int topic, void *argument = nullptr) = 0;
  };

}} // namespace Thing::Rx
