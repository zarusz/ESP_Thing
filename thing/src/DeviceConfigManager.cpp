#include "DeviceConfigManager.h"
#include <WiFiManager.h> //https://github.com/tzapu/WiFiManager

auto shouldSaveConfig = false;

DeviceConfigManager::DeviceConfigManager(DeviceConfig *deviceConfig) {
  _deviceConfig = deviceConfig;
  _deviceConfigLoaded = false;
}

bool DeviceConfigManager::EnsureConfigLoaded() {
  if (!_deviceConfigLoaded) {
    if (_deviceConfig->Load())
      _deviceConfigLoaded = true;
    else
      Serial.println(
          "[DeviceConfigManager] Failed to load config from file system");
  }
  return _deviceConfigLoaded;
}

bool DeviceConfigManager::EnterConfigMode() {
  // See https://github.com/tzapu/WiFiManager

  Serial.println("[DeviceConfigManager] Entering config mode...");

#define MAX_LEN 32
  char unique_id[MAX_LEN] = "";
  char mqtt_host[MAX_LEN] = "192.168.1.120";
  char mqtt_port[MAX_LEN] = "1883";
  char mqtt_user[MAX_LEN] = "";
  char mqtt_pass[MAX_LEN] = "";

  if (_deviceConfig->UniqueId.length() > 0)
    strcpy(unique_id, _deviceConfig->UniqueId.c_str());

  if (_deviceConfig->MqttHost.length() > 0)
    strcpy(mqtt_host, _deviceConfig->MqttHost.c_str());

  if (_deviceConfig->MqttPort > 0)
    sprintf(mqtt_port, "%d", _deviceConfig->MqttPort);

  if (_deviceConfig->MqttUser.length() > 0)
    strcpy(mqtt_user, _deviceConfig->MqttUser.c_str());

  if (_deviceConfig->MqttPass.length() > 0)
    strcpy(mqtt_pass, _deviceConfig->MqttPass.c_str());

  // WiFiManager
  // Local intialization. Once its business is done, there is no need to keep it
  // around
  WiFiManager wifiManager;

  // The extra parameters to be configured (can be either global or just in the
  // setup) After connecting, parameter.getValue() will get you the configured
  // value id/name placeholder/prompt default length
  WiFiManagerParameter param_unique_id("UniqueId", "Unique Id", unique_id,
                                       MAX_LEN);
  WiFiManagerParameter param_mqtt_host("MqttHost", "MQTT Server", mqtt_host,
                                       MAX_LEN);
  WiFiManagerParameter param_mqtt_port("MqttPort", "MQTT Port", mqtt_port,
                                       MAX_LEN);
  WiFiManagerParameter param_mqtt_user("MqttUser", "MQTT User", mqtt_user,
                                       MAX_LEN);
  WiFiManagerParameter param_mqtt_pass("MqttPass", "MQTT Password", mqtt_pass,
                                       MAX_LEN);

  wifiManager.addParameter(&param_unique_id);
  wifiManager.addParameter(&param_mqtt_host);
  wifiManager.addParameter(&param_mqtt_port);
  wifiManager.addParameter(&param_mqtt_user);
  wifiManager.addParameter(&param_mqtt_pass);

  // reset settings - for testing
  // wifiManager.resetSettings();

  // it starts an access point with the specified name
  // here  "AutoConnectAP"
  // and goes into a blocking loop awaiting configuration
  wifiManager.setSaveConfigCallback([] { shouldSaveConfig = true; });

  if (!wifiManager.startConfigPortal("OnDemandAP")) {
    Serial.println("[DeviceConfigManager] Failed to connect and hit timeout");
    delay(3000);
    // reset and try again, or maybe put it to deep sleep
    ESP.reset();
    delay(5000);
  }

  if (shouldSaveConfig) {
    Serial.println("[DeviceConfigManager] Saving config");

    _deviceConfig->UniqueId = param_unique_id.getValue();
    _deviceConfig->MqttHost = param_mqtt_host.getValue();
    _deviceConfig->MqttPort = atoi(param_mqtt_port.getValue());
    _deviceConfig->MqttUser = param_mqtt_user.getValue();
    _deviceConfig->MqttPass = param_mqtt_pass.getValue();

    if (!_deviceConfig->Save())
      Serial.println("[DeviceConfigManager] Failed save config to file system");

    return true;
  }

  // if you get here you have connected to the WiFi
  return false;
}
