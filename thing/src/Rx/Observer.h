#pragma once

#include "IObservable.h"
#include "IObserver.h"
#include <set>

namespace Thing { namespace Rx {

  class Observable : public IObservable {
  protected:
    std::set<IObserver *> _observers;

  public:
    Observable();

    virtual void Subscribe(IObserver *observer);
    virtual void Unsubscribe(IObserver *observer);
    virtual void Notify(const int topic, void *argument = nullptr);
  };

}} // namespace Thing::Rx