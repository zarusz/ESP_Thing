#include "DeviceConfig.h"
#include <ArduinoJson.h>
#include <functional>
#include "FS.h"

#define CONFIG_FILE             "/config.json"
#define CONFIG_FILE_MAX_SIZE    1024

DeviceConfig::DeviceConfig()
{
}

bool DeviceConfig::Load()
{
   File configFile = SPIFFS.open(CONFIG_FILE, "r");
   if (!configFile) {
     Serial.println("Failed to open config file");
     return false;
   }

   size_t size = configFile.size();
   if (size > CONFIG_FILE_MAX_SIZE) {
     Serial.println("Config file size is too large");
     return false;
   }

   // Allocate a buffer to store contents of the file.
   std::unique_ptr<char[]> buf(new char[size]);
   configFile.readBytes(buf.get(), size);

   StaticJsonBuffer<CONFIG_FILE_MAX_SIZE> jsonBuffer;
   JsonObject& json = jsonBuffer.parseObject(buf.get());

   if (!json.success()) {
     Serial.println("Failed to parse config file");
     return false;
   }

   UniqueId = json["UniqueId"].asString();
   MqttHost = json["MqttHost"].asString();
   MqttPort = json["MqttPort"].as<int>();
   MqttUser = json["MqttUser"].asString();
   MqttPass = json["MqttPass"].asString();

   Serial.print("Loaded configs from file system. ");
   Serial.print("UniqueId: ");
   Serial.print(UniqueId.c_str());
   Serial.print(", MqttHost: ");
   Serial.print(MqttHost.c_str());
   Serial.print(", MqttPort: ");
   Serial.print(MqttPort);
   Serial.print(", MqttUser: ");
   Serial.print(MqttUser.c_str());
   Serial.print(", MqttPass: ");
   Serial.print(MqttPass.c_str());
   Serial.println(".");

   return true;
}

bool DeviceConfig::SaveInternal(std::function<void(File&)> operation)
{
  File configFile = SPIFFS.open(CONFIG_FILE, "w");
  if (!configFile) {
    Serial.println("Failed to open config file for writing");
    return false;
  }

  operation(configFile);

  configFile.close();

  return true;
}

bool DeviceConfig::Save(String& json)
{
  return SaveInternal([&](File& configFile) {
    configFile.write((uint8_t*) json.c_str(), json.length());
  });
}

bool DeviceConfig::Save()
{
  StaticJsonBuffer<CONFIG_FILE_MAX_SIZE> jsonBuffer;

  JsonObject& json = jsonBuffer.createObject();
  json["UniqueId"] = UniqueId.c_str();
  json["MqttHost"] = MqttHost.c_str();
  json["MqttPort"] = MqttPort;
  json["MqttUser"] = MqttUser.c_str();
  json["MqttPass"] = MqttPass.c_str();

  return SaveInternal([&](File& configFile) {
    json.printTo(configFile);
  });
}
