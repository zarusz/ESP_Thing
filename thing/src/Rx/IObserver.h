#pragma once

#include "IObserver.h"

namespace Thing { namespace Rx {

  class IObserver {
  public:
    virtual void OnNotified(void *observable, const int topic,
                            void *argument) = 0;
  };

}} // namespace Thing::Rx
