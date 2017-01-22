#include "MqttMessageBus.h"
#include <ESP8266HTTPClient.h>
#include <algorithm>

#include "../DeviceCommands.pb.h"
#include "../Utils/TimeUtil.h"


// We assume there will be only 1 instance crated.
// We need this in the static
MqttMessageBus* Singleton;

MqttMessageBus::MqttMessageBus(DeviceConfig* deviceConfig, MessageHandler* handler)
  : _mqttClient(_espClient)
{
  _deviceConfig = deviceConfig;
  SetHandler(handler);

  _mqttClient.setServer(_deviceConfig->MqttHost.c_str(), _deviceConfig->MqttPort);
  _mqttClient.setCallback(MqttMessageCallback);

  // We will need the reference in the static callback
  Singleton = this;
}

MqttMessageBus::~MqttMessageBus()
{
}

bool MqttMessageBus::IsConnected()
{
  return _mqttClient.connected();
}

bool MqttMessageBus::Publish(const char* topic, const Buffer& payload)
{
  Serial.printf("[MQTT] Publish to topic: '%s', payload size: %d\n", topic, payload.Size());
  bool success = _mqttClient.publish(topic, payload.Data(), payload.Size());
  if (!success) {
    Serial.println("[MQTT] Publish failed");
  }
  return success;
}

bool MqttMessageBus::Publish(const char* topic, const String& payload, bool retained)
{
  return Publish(topic, payload.c_str(), retained);
}

bool MqttMessageBus::Publish(const char* topic, const char* message, bool retained)
{
  Serial.printf("[MQTT] Publish to topic: '%s', payload size: %d, payload: %s\n", topic, strlen(message), message);
  bool success = _mqttClient.publish(topic, message, retained);
  if (!success) {
    Serial.println("[MQTT] Publish failed");
  }
  return success;
}

void MqttMessageBus::Subscribe(const char* topic)
{
  if (_topics.find(topic) != _topics.end())
  {
    // already subscribed
    return;
  }

  // remember the topics we subscribed
  _topics.insert(topic);
  // if mqtt connected then subscribe
  if (_mqttClient.connected())
  {
    _mqttClient.subscribe(topic);
  }
}

void MqttMessageBus::Unsubscribe(const char* topic)
{
  // forget about the topic
  if (_topics.erase(topic) > 0)
  {
    // if mqtt connected then unsubscribe
    if (_mqttClient.connected())
    {
      _mqttClient.unsubscribe(topic);
    }
  }
}

void MqttMessageBus::ReconnectMqtt()
{
  // Loop until we're reconnected
   while (!_mqttClient.connected())
   {
     // Create a random client ID
     String clientId = String(_deviceConfig->UniqueId) + "-" + String(random(0xffff), HEX);
     Serial.printf("[MQTT] Attempting MQTT connection (ClientId: %s)...\n", clientId.c_str());

     // Attempt to connect
     bool connected;
     if (_willTopic && _willMessage) {
       connected = _mqttClient.connect(clientId.c_str(), _deviceConfig->MqttUser.c_str(), _deviceConfig->MqttPass.c_str(), _willTopic, MQTTQOS1, _willRetain, _willMessage);
     } else {
       connected = _mqttClient.connect(clientId.c_str(), _deviceConfig->MqttUser.c_str(), _deviceConfig->MqttPass.c_str());
     }

     if (connected)
     {
       Serial.println("[MQTT] Connected");

       // Subscribe to all the topics
       std::for_each(_topics.begin(), _topics.end(), [this](const char* topic) {
         Serial.printf("[MQTT] Subscribing to topic: '%s'\n", topic);
         _mqttClient.subscribe(topic);
       });
     }
     else
     {
       Serial.print("[MQTT] Failed to connect, rc=");
       Serial.print(_mqttClient.state());
       Serial.println(" try again in 5 seconds");
       // Wait 5 seconds before retrying
       delay(5000);
     }
   }
}

void MqttMessageBus::Loop()
{
  if (!_mqttClient.connected())
  {
    ReconnectMqtt();
  }
  _mqttClient.loop();
}

void MqttMessageBus::MqttMessageCallback(char* topic, byte* payload, uint length)
{
  if (Singleton)
    Singleton->MessageCallback(topic, payload, length);
}

void MqttMessageBus::MessageCallback(char* topic, byte* payload, uint length)
{
  Serial.printf("[MQTT] Received on topic: '%s', message with length: '%d'\n", topic, length);

  _buffer.Resize(length);
  memcpy(_buffer.Data(), payload, length);

  // check if message handler is set
  if (_handler)
      _handler->Handle(topic, _buffer);
}

void MqttMessageBus::SetWill(const char* topic, const char* message, bool retain)
{
  _willTopic = topic;
  _willMessage = message;
  _willRetain = retain;
}
