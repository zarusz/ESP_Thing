#include <Arduino.h>

#include <WiFiManager.h>          //https://github.com/tzapu/WiFiManager

#include "MainApp.h"
#include "DeviceConfig.h"
#include "FS.h"

DeviceConfig deviceConfig;
bool deviceConfigLoaded;
MainApp* mainApp;

// select which pin will trigger the configuration portal when set to LOW
// ESP-01 users please note: the only pins available (0 and 2), are shared
// with the bootloader, so always set them HIGH at power-up
#define TRIGGER_PIN 0

void setup()
{
	Serial.begin(115200);
	//pinMode(LED_BUILTIN, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
	delay(200);

	Serial.print("Starting IoT device: ");
	Serial.println(ESP.getChipId());

	pinMode(TRIGGER_PIN, INPUT);

	Serial.println("Mounting file system...");
	if (!SPIFFS.begin())
	{
		Serial.println("Failed to mount file system");
		return;
	}

	deviceConfigLoaded = false;
}

bool isEnterConfigMode()
{
	return digitalRead(TRIGGER_PIN) == LOW;
}

auto shouldSaveConfig = false;

bool configMode()
{
	// See https://github.com/tzapu/WiFiManager

	Serial.println("Entering config mode...");

	#define MAX_LEN 32
  char unique_id[MAX_LEN] = "";
	char mqtt_host[MAX_LEN] = "192.168.1.120";
	char mqtt_port[MAX_LEN] = "1883";
	char mqtt_user[MAX_LEN] = "";
	char mqtt_pass[MAX_LEN] = "";

	if (deviceConfig.UniqueId.length() > 0)
			strcpy(unique_id, deviceConfig.UniqueId.c_str());

	if (deviceConfig.MqttHost.length() > 0)
			strcpy(mqtt_host, deviceConfig.MqttHost.c_str());

	if (deviceConfig.MqttPort > 0)
			sprintf(mqtt_port, "%d", deviceConfig.MqttPort);

	if (deviceConfig.MqttUser.length() > 0)
			strcpy(mqtt_user, deviceConfig.MqttUser.c_str());

	if (deviceConfig.MqttPass.length() > 0)
			strcpy(mqtt_pass, deviceConfig.MqttPass.c_str());

	//WiFiManager
	//Local intialization. Once its business is done, there is no need to keep it around
	WiFiManager wifiManager;

	// The extra parameters to be configured (can be either global or just in the setup)
  // After connecting, parameter.getValue() will get you the configured value
  // id/name placeholder/prompt default length
  WiFiManagerParameter param_unique_id("UniqueId", "Unique Id", unique_id, MAX_LEN);
  WiFiManagerParameter param_mqtt_host("MqttHost", "MQTT Server", mqtt_host, MAX_LEN);
  WiFiManagerParameter param_mqtt_port("MqttPort", "MQTT Port", mqtt_port, MAX_LEN);
  WiFiManagerParameter param_mqtt_user("MqttUser", "MQTT User", mqtt_user, MAX_LEN);
  WiFiManagerParameter param_mqtt_pass("MqttPass", "MQTT Password", mqtt_pass, MAX_LEN);

	wifiManager.addParameter(&param_unique_id);
	wifiManager.addParameter(&param_mqtt_host);
  wifiManager.addParameter(&param_mqtt_port);
	wifiManager.addParameter(&param_mqtt_user);
	wifiManager.addParameter(&param_mqtt_pass);

	//reset settings - for testing
	//wifiManager.resetSettings();

	//it starts an access point with the specified name
	//here  "AutoConnectAP"
	//and goes into a blocking loop awaiting configuration
 	wifiManager.setSaveConfigCallback([]{ shouldSaveConfig = true; });

	if (!wifiManager.startConfigPortal("OnDemandAP"))
	{
		Serial.println("Failed to connect and hit timeout");
		delay(3000);
		//reset and try again, or maybe put it to deep sleep
		ESP.reset();
		delay(5000);
	}

	if (shouldSaveConfig)
	{
     Serial.println("Saving config");

		 deviceConfig.UniqueId = param_unique_id.getValue();
		 deviceConfig.MqttHost = param_mqtt_host.getValue();
		 deviceConfig.MqttPort = atoi(param_mqtt_port.getValue());
		 deviceConfig.MqttUser = param_mqtt_user.getValue();
		 deviceConfig.MqttPass = param_mqtt_pass.getValue();

     if (!deviceConfig.Save())
       Serial.println("Failed save config to file system");

		 return true;
   }

	//if you get here you have connected to the WiFi
	return false;
}

void loop()
{
	if (!deviceConfigLoaded)
	{
		if (deviceConfig.Load())
			deviceConfigLoaded = true;
		else
			Serial.println("Failed to load config from file system");
	}

	if (isEnterConfigMode() || !deviceConfigLoaded)
	{
		if (configMode())
		{
			if (mainApp != NULL) {
				delete mainApp;
				mainApp = NULL;
			}
		}
	}

  if (mainApp == NULL)
	{
		mainApp = new MainApp(&deviceConfig);
	}

	if (mainApp->EnsureConnected(30000, []{ return !isEnterConfigMode(); }))
	{
		mainApp->Loop();
	}
	else
	{
		// TODO: Sleep
	}
}
