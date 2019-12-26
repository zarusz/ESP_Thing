// MainApp.h

#ifndef _MAINAPP_h
#define _MAINAPP_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#include <ESP8266WiFi.h>
#include <PubSubClient.h>

class MainApp {
private:

	const char* networkSsid;
	const char* networkPassword;
	const char* mqttServer;
	const char* nodeId;

	WiFiClient espClient;
	PubSubClient pubSubClient;

	long lastMsg = 0;
	char msg[256];
	int value = 0;

public:
	MainApp(const char * newtworkSsid, const char * networkPassword, const char * mqttServer, const char * nodeId, MQTT_CALLBACK_SIGNATURE);
	virtual ~MainApp();

	void Init();
	void Loop();

	virtual void Callback(char* topic, byte* payload, unsigned int length);

protected:
	void SetupWifi();
	void ReconnectPubSub();
};


#endif

