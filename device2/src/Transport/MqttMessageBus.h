#ifndef _MqttMessageBus_h
#define _MqttMessageBus_h

#include <Arduino.h>
#include <set>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <pb.h>

#include "MessageBus.h"
#include "PbSerializer.h"

class MqttMessageBus : public MessageBus
{
protected:
	const char* _serverHost;
	int _serverPort;
	const char* _deviceId;
	std::set<const char*> _topics;
	Serializer& _serializer;
	std::vector<byte> _buffer;

	WiFiClient _espClient;
	PubSubClient _mqttClient;

	void ReconnectMqtt();
	static void MqttMessageCallback(char* topic, byte* payload, uint length);
	void MessageCallback(char* topic, byte* payload, uint length);

public:
	MqttMessageBus(const char* serverHost, int serverPort, MessageHandler* handler, const char* deviceId, Serializer& serializer);
	virtual ~MqttMessageBus();

public:
	virtual bool Publish(const char* topic, const std::vector<byte>& payload);
	virtual bool Publish(const char* topic, const void* message);
	virtual void Subscribe(const char* topic);
	virtual void Unsubscribe(const char* topic);

	virtual void Loop();
};

#endif
