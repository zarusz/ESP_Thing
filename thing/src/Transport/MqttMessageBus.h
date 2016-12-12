#ifndef _MqttMessageBus_h
#define _MqttMessageBus_h

#include <Arduino.h>
#include <set>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <pb.h>

#include "MessageBus.h"
#include "Buffer.h"

class MqttMessageBus : public MessageBus
{
protected:
	const char* _serverHost;
	int _serverPort;

	const char* _deviceId;
	std::set<const char*> _topics;
	StaticBuffer<256> _buffer;

	const char* _willTopic;
	const char* _willMessage;
	bool _willRetain;

	WiFiClient _espClient;
	PubSubClient _mqttClient;

	void ReconnectMqtt();
	static void MqttMessageCallback(char* topic, byte* payload, uint length);
	void MessageCallback(char* topic, byte* payload, uint length);

public:
	MqttMessageBus(const char* serverHost, int serverPort, MessageHandler* handler, const char* deviceId);
	virtual ~MqttMessageBus();

public:
	virtual bool Publish(const char* topic, const Buffer& payload);
	virtual bool Publish(const char* topic, const String& payload, bool retained = false);
	virtual bool Publish(const char* topic, const char* message, bool retained = false);
	virtual void Subscribe(const char* topic);
	virtual void Unsubscribe(const char* topic);

	virtual void Loop();

public:
	void SetWill(const char* topic, const char* message, bool retain = true);
};

#endif
