#ifndef _MessageBus_h
#define _MessageBus_h

#include <Arduino.h>
#include <ArduinoJson.h>
#include <pb.h>
#include <vector>

#include "PbSerializer.h"

class MessageHandler
{
public:
	virtual void Handle(const char* topic, const std::vector<byte>& payload, Serializer& serializer) = 0;
};

class MessageBus
{
protected:
	MessageHandler* _handler;

public:
	virtual bool Publish(const char* topic, const std::vector<byte>& payload) = 0;
	virtual bool Publish(const char* topic, const void* message) = 0;
	virtual void Subscribe(const char* topic) = 0;
	virtual void Unsubscribe(const char* topic) = 0;
	virtual void SetHandler(MessageHandler* handler);
};

#endif
