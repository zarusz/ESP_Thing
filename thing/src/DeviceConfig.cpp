#include "DeviceConfig.h"
#include "FS.h"
#include <ArduinoJson.h>
#include <functional>

#define CONFIG_FILE "/config.json"
#define CONFIG_FILE_MAX_SIZE 1024

DeviceConfig::DeviceConfig() {
}

bool DeviceConfig::Load() {
  File configFile = SPIFFS.open(CONFIG_FILE, "r");
  if (!configFile) {
    Serial.println("Config: Failed to open config file");
    return false;
  }

  size_t size = configFile.size();
  if (size > CONFIG_FILE_MAX_SIZE) {
    Serial.println("Config: Confog file size is too large");
    return false;
  }

  // Allocate a buffer to store contents of the file.
  char configFileContent[CONFIG_FILE_MAX_SIZE];
  configFile.readBytes(configFileContent, size);

  Serial.println("Config: Loaded config from file system:");
  Serial.write(configFileContent, size);
  Serial.println("");

  StaticJsonBuffer<CONFIG_FILE_MAX_SIZE> jsonBuffer;
  JsonObject &json = jsonBuffer.parseObject(configFileContent);

  if (!json.success()) {
    Serial.println("Config: Failed to parse config file");
    return false;
  }

  UniqueId = json["UniqueId"].as<char*>();
  MqttHost = json["MqttHost"].as<char*>();
  MqttPort = json["MqttPort"].as<int>();
  MqttUser = json["MqttUser"].as<char*>();
  MqttPass = json["MqttPass"].as<char*>();

  return true;
}

bool DeviceConfig::SaveInternal(std::function<void(File &)> operation) {
  File configFile = SPIFFS.open(CONFIG_FILE, "w");
  if (!configFile) {
    Serial.println("Failed to open config file for writing");
    return false;
  }

  operation(configFile);

  configFile.close();

  return true;
}

bool DeviceConfig::Save(String &json) {
  return SaveInternal([&](File &configFile) {
    configFile.write((uint8_t *)json.c_str(), json.length());
  });
}

bool DeviceConfig::Save() {
  StaticJsonBuffer<CONFIG_FILE_MAX_SIZE> jsonBuffer;

  JsonObject &json = jsonBuffer.createObject();
  json["UniqueId"] = UniqueId.c_str();
  json["MqttHost"] = MqttHost.c_str();
  json["MqttPort"] = MqttPort;
  json["MqttUser"] = MqttUser.c_str();
  json["MqttPass"] = MqttPass.c_str();

  return SaveInternal([&](File &configFile) { json.printTo(configFile); });
}
