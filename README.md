# What is ESP_Thing?
It is meant for makers of IoT things. It is a basline implementation (C++) targeting the ESP8266 chip. 

The implementation supports some basic hardware (DHT-22 Himidity & Temperature sensors, LEDs, RGB LED strips, relays). 

Communcation with the device ESP_Thing device is established using the MQTT protocol over WiFi.     
It can be easily connected with Smart Home platform [openHAB](https://www.openhab.org/) using the [MQTT addon](https://www.openhab.org/addons/bindings/mqtt/). Since MQTT it's platform agnostic it could work with other smart home platforms (HomeKit).

# History

For my home I needed to build multiple custom hardware things running on ESP8266 and integrate that into the openHAB platform.
I have few devices that are running for several years now. 

# Compiling

It is recommended to use [Visual Studio Code](https://code.visualstudio.com/) with the [Platform IO IDE extension](https://marketplace.visualstudio.com/items?itemName=platformio.platformio-ide).

# Using


