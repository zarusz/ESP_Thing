#include <Arduino.h>
#include "MainApp.h"
#include "DeviceConfig.h"
#include "FS.h"

DeviceConfig* deviceConfig;
MainApp* mainApp;

void setup()
{
	Serial.begin(115200);
	//pinMode(LED_BUILTIN, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
	delay(200);

	Serial.println("");
	Serial.println("Mounting file system...");
	if (!SPIFFS.begin()) {
		Serial.println("Failed to mount file system");
		return;
	}

	deviceConfig = new DeviceConfig();
	if (!deviceConfig->ReadFromFileSystem()) {
		Serial.println("Failed to read config from file system");
	}

	mainApp = new MainApp(deviceConfig);
	mainApp->Init();
}

void loop()
{
	mainApp->Loop();
}
