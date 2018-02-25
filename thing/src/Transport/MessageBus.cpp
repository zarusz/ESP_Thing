#include "MessageBus.h"

void MessageBus::SetHandler(MessageHandler *handler) {
  _handler = handler;
}
