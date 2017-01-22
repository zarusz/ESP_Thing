#include "DeviceConfig.h"
#include <ArduinoJson.h>
#include "FS.h"

#define CONFIG_FILE   "/config.json"

DeviceConfig::DeviceConfig()
{
}

DeviceConfig::DeviceConfig(const char* uniqueId,
  const char* wifiName, const char* wifiPassword,
  const char* mqttHost, const int mqttPort,
  const char* mqttUser, const char* mqttPass)
{
  UniqueId = uniqueId;
  WifiName = wifiName;
  WifiPassword = wifiPassword;
  MqttHost = mqttHost;
  MqttPort = mqttPort;
  MqttUser = mqttUser;
  MqttPass = mqttPass;
}

bool DeviceConfig::ReadFromFileSystem()
{
   File configFile = SPIFFS.open(CONFIG_FILE, "r");
   if (!configFile) {
     Serial.println("Failed to open config file");
     return false;
   }

   size_t size = configFile.size();
   if (size > 1024) {
     Serial.println("Config file size is too large");
     return false;
   }

   // Allocate a buffer to store contents of the file.
   std::unique_ptr<char[]> buf(new char[size]);
   configFile.readBytes(buf.get(), size);

   StaticJsonBuffer<512> jsonBuffer;
   JsonObject& json = jsonBuffer.parseObject(buf.get());

   if (!json.success()) {
     Serial.println("Failed to parse config file");
     return false;
   }

   UniqueId = json["UniqueId"].asString();
   WifiName = json["WifiName"].asString();
   WifiPassword = json["WifiPassword"].asString();
   MqttHost = json["MqttHost"].asString();
   MqttPort = json["MqttPort"].as<int>();
   MqttUser = json["MqttUser"].asString();
   MqttPass = json["MqttPass"].asString();

   Serial.print("Loaded configs from file system. ");
   Serial.print("UniqueId: ");
   Serial.print(UniqueId);
   Serial.print(", WifiName: ");
   Serial.print(WifiName.c_str());
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

bool DeviceConfig::WriteToFileSystem(String& json)
{
  File configFile = SPIFFS.open(CONFIG_FILE, "w");
  if (!configFile) {
    Serial.println("Failed to open config file for writing");
    return false;
  }

  configFile.write((uint8_t*) json.c_str(), json.length());
  configFile.close();

  return true;
}
