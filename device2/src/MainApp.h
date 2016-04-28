// MainApp.h

#ifndef _MAINAPP_h
#define _MAINAPP_h

#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "DeviceConfig.h"
#include "DeviceCommands.pb.h"
#include "CommHub.h"
#include "DeviceContext.h"
#include "FeatureControllers/FeatureController.h"
#include <vector>
#include <memory>
#include "Pins/Pins.h"
#include "Pins/ShiftRegisterPins.h"

class MainApp : public CommHub, public DeviceContext
{
private:
	DeviceConfig deviceConfig;
	WiFiClient espClient;
	PubSubClient pubSubClient;
	String deviceInTopic;
	DeviceDescription deviceDescription;
	std::vector<FeatureController*> features;
	ShiftRegisterPins pins;

	long lastMsg = 0;
	char msg[256];
	int value = 0;

public:
	MainApp(MQTT_CALLBACK_SIGNATURE);
	virtual ~MainApp();

	void Init();
	void Loop();

	virtual void Callback(char* topic, byte* payload, unsigned int length);

	virtual DeviceConfig& GetConfig() { return deviceConfig; }
  virtual CommHub& GetCommHub() { return *this; }
	virtual Pins& GetPins() { return pins; }

protected:
	void SetupWifi();
	void ReconnectPubSub();

	bool DecodeMessage(byte* payload, unsigned int length, const pb_field_t* msg_fields, void* msg) const;
	bool EncodeMessage(byte* payload, unsigned int maxLength, unsigned int& length, const pb_field_t* msg_fields, const void* msg) const;

	void DebugRetrievedMessage(const char* topic, byte* payload, unsigned int length);
	void HandleDeviceMessage(DeviceMessage& deviceMessage);

	void OnStart();
	void OnStop();
	void OnLoop();

public:
	bool PublishMessage(const char* topic, const pb_field_t* msg_fields, const void* msg);
};


#endif
