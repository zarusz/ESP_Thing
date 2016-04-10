// MainApp.h

#ifndef _MAINAPP_h
#define _MAINAPP_h

#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "DeviceConfig.h"
#include "DeviceCommands.pb.h"

class MainApp {
private:
	DeviceConfig deviceConfig;
	WiFiClient espClient;
	PubSubClient pubSubClient;

	String deviceInTopic;

	DeviceDescription deviceDescription;

	long lastMsg = 0;
	char msg[256];
	int value = 0;

public:
	MainApp(MQTT_CALLBACK_SIGNATURE);
	virtual ~MainApp();

	void Init();
	void Loop();

	virtual void Callback(char* topic, byte* payload, unsigned int length);

protected:
	void SetupWifi();
	void ReconnectPubSub();

	bool DecodeMessage(byte* payload, unsigned int length, const pb_field_t* msg_fields, void* msg) const;
	bool EncodeMessage(byte* payload, unsigned int maxLength, unsigned int& length, const pb_field_t* msg_fields, const void* msg) const;

  bool PublishMessage(const char* topic, const pb_field_t* msg_fields, const void* msg);

	void DebugRetrievedMessage(const char* topic, byte* payload, unsigned int length);
	void HandleDeviceMessage(DeviceMessage& deviceMessage);

	void OnStart();
	void OnStop();
	void OnLoop();
};


#endif
