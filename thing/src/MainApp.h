// MainApp.h

#ifndef _MAINAPP_h
#define _MAINAPP_h

#include <Arduino.h>
#include <vector>
#include <memory>

#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ESP8266httpUpdate.h>

#include "DeviceConfig.h"
#include "DeviceCommands.pb.h"
#include "DeviceContext.h"
#include "FeatureControllers/FeatureController.h"
#include "Transport/MqttMessageBus.h"
#include "Transport/PbSerializer.h"
#include "Pins/Pins.h"
#include "Pins/ShiftRegisterPins.h"

enum DeviceState
{
	New,
	Started,
	Running,
	Stopped
};

class MainApp : public DeviceContext, public MessageHandler
{
private:
	DeviceConfig _deviceConfig;
	String _deviceCommandTopic;
	String _deviceCommandTopicSub;
	String _deviceStateTopic;
	String _deviceServiceTopic;
	String _deviceServiceTopicSub;
	MqttMessageBus _messageBus;
	PbSerializer _serializer;

	std::vector<FeatureController*> _features;
	ShiftRegisterPins _pins;

	ulong _lastMsg = 0;
	int _value = 0;

	DeviceState _state;

public:
	MainApp();
	virtual ~MainApp();

	void Init();
	void Loop();

	virtual DeviceConfig& GetConfig() { return _deviceConfig; }
  virtual MessageBus* GetMessageBus() { return &_messageBus; }
	virtual Pins& GetPins() { return _pins; }
	virtual const String& GetCommandTopic() const { return _deviceCommandTopic; }
	virtual const String& GetStateTopic() const { return _deviceStateTopic; }

	virtual void Handle(const char* topic, const Buffer& payload, Serializer& serializer);

protected:
	void SetupWifi();
	void ReconnectPubSub();

	//void DebugRetrievedMessage(const char* topic, const void* message);
	void HandleDeviceMessage(const char* topic, const Buffer& payload);
	void HandleServiceCommand(const char* topic, const Buffer& payload);
	void HandleUpgradeCommand(const UpgradeFirmwareCommand& message);
	void HandleStatusRequest(const DeviceStatusRequest& message);

	void OnStart();
	void OnStop();
	void OnLoop();

	void SendDescription();
	void SendHearbeat();
};


#endif
