#pragma once

#include "Buffer.h"
#include <Arduino.h>

class MessageHandler {
public:
  virtual void Handle(const char *topic, const Buffer &payload) = 0;
};

class MessageBus {
protected:
  MessageHandler *_handler;

public:
  virtual bool IsConnected() = 0;

  virtual bool Publish(const char *topic, const Buffer &payload) = 0;
  virtual bool Publish(const char *topic, const String &payload,
                       bool retained = false) = 0;
  virtual bool Publish(const char *topic, const char *message,
                       bool retained = false) = 0;

  virtual void Subscribe(const char *topic) = 0;
  virtual void Unsubscribe(const char *topic) = 0;
  virtual void SetHandler(MessageHandler *handler);
};
