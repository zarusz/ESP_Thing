#include <Arduino.h>
#include <WiFiManager.h> //https://github.com/tzapu/WiFiManager

#include "DeviceConfig.h"
#include "DeviceConfigManager.h"
#include "FS.h"
#include "FeatureControllers/Led.h"
#include "FeatureControllers/PushButton.h"
#include "MainApp.h"

using namespace Thing;
using namespace Thing::FeatureControllers;

DeviceConfig deviceConfig;
DeviceConfigManager deviceConfigManager(&deviceConfig);

// PushButton will trigger the configuration portal when set to LOW
PushButton pushButton;
Led statusLed;
MainApp *mainApp;

void displayInfo() {
  uint32_t realSize = ESP.getFlashChipRealSize();
  uint32_t ideSize = ESP.getFlashChipSize();
  FlashMode_t ideMode = ESP.getFlashChipMode();

  Serial.printf("Flash real id:   %08X\n", ESP.getFlashChipId());
  Serial.printf("Flash real size: %u\n\n", realSize);
  Serial.printf("Flash ide  size: %u\n", ideSize);
  Serial.printf("Flash ide speed: %u\n", ESP.getFlashChipSpeed());
  Serial.printf("Flash ide mode:  %s\n", (ideMode == FM_QIO ? "QIO" : ideMode == FM_QOUT ? "QOUT" : ideMode == FM_DIO ? "DIO" : ideMode == FM_DOUT ? "DOUT" : "UNKNOWN"));
  Serial.printf("Flash Chip configuration %s\n", ideSize != realSize ? "ok." : "wrong!");
}

void setup() {
  Serial.begin(115200);
  // pinMode(LED_BUILTIN, OUTPUT);     // Initialize the BUILTIN_LED pin as an
  delay(200);

  Serial.printf("\n[Main] Starting IoT device: %08X\n", ESP.getChipId());

  displayInfo();

  pushButton.Start();
  statusLed.Start();
  statusLed.SetMode("110000000000");

  Serial.println("[Main] Mounting file system...");
  if (!SPIFFS.begin()) {
    Serial.println("[Main] Failed to mount file system");
    return;
  }
}

void loop() {
  statusLed.Loop();

  if (!deviceConfigManager.EnsureConfigLoaded() || pushButton.IsLongPushed()) {
    pushButton.ClearLongPushed();

    if (deviceConfigManager.EnterConfigMode()) {
      if (mainApp != NULL) {
        delete mainApp;
        mainApp = NULL;
      }
    }
  }

  if (mainApp == NULL) {
    mainApp = new MainApp(&deviceConfig, &pushButton, &statusLed);
  }

  if (mainApp->EnsureConnected(30000, [] { return !pushButton.IsLongPushed(); })) {
    mainApp->Loop();
  }
}
