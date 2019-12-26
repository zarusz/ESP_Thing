# What is ESP_Thing?
It is meant for makers of IoT things. It is a basline implementation (C++) targeting the ESP8266 chip. 

The implementation supports some basic hardware (DHT-22 Himidity & Temperature sensors, LEDs, RGB LED strips, relays). 

Communcation with the device ESP_Thing device is established using the MQTT protocol over WiFi.     
It can be easily connected with Smart Home platform [openHAB](https://www.openhab.org/) using the [MQTT addon](https://www.openhab.org/addons/bindings/mqtt/). Since MQTT is platform agnostic it could work with other smart home platforms (HomeKit).

# History

For my home I needed to build multiple custom hardware things running on ESP8266 and integrate that into the openHAB platform.
I have few devices that are running for several years now. Also, I have few Sonoff Switch running the ESP_Thing software (instead of the stock firmware). 

# Using

It is recommended to use [Visual Studio Code](https://code.visualstudio.com/) with the [Platform IO IDE extension](https://marketplace.visualstudio.com/items?itemName=platformio.platformio-ide). 

As a result of compilation you get the bin file that can be used to flash the ESP8266 device.
Alternatively, once the ESP_Think is on the device, you can use the Over the Air Updates (OTA) to push never firmware updates.

# Features

* Smart Config
  * With first run (or after a push button is pressed and hold during boot) the device enter config mode.
  * It starts a open WiFi network you can join to from your mobile phone
  * It displays a configuration website where you can enter basic settings (WiFi name & pass, device name).
* Over the Air Updates (OTA)
  * You can send a command to the device with a URL from which it should take the latest ROM.
 


