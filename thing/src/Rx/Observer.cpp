#include "Observer.h"
#include <algorithm>

using namespace Thing::Rx;

Observable::Observable() {
}

void Observable::Subscribe(IObserver *observer) {
  _observers.insert(observer);
}

void Observable::Unsubscribe(IObserver *observer) {
  _observers.erase(observer);
}

void Observable::Notify(const int topic, void *argument) {
    for_each(_observers.cbegin(), _observers.cend(), [this, topic, argument](IObserver *observer) {
        observer->OnNotified(this, topic, argument);
    });
}
