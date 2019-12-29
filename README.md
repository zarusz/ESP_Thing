![Build Status](https://api.travis-ci.com/zarusz/ESP_Thing.svg?branch=master "Build Status")

# What is ESP_Thing?
It is meant for makers of IoT things. It is a basline implementation (C++) targeting the ESP8266 chip (Arduino Espressif8266 Framework).

The implementation out of the box supports basic sensors and actuators (DHT-22 Himidity & Temperature sensors, LEDs, RGB LED strips, relays/switches), with ability to extend.

Communcation with the device ESP_Thing device is established using the MQTT protocol over WiFi.     
It can be easily connected with Smart Home platform [openHAB](https://www.openhab.org/) using the [MQTT addon](https://www.openhab.org/addons/bindings/mqtt/). Since MQTT is platform agnostic it could work with other smart home platforms (HomeKit).

# History

When doing my smart home I needed to build multiple custom hardware things running on ESP8266 and integrate that into the openHAB platform. Some of those devices are Switches running the ESP_Thing software (instead of the stock firmware). 
This has been running succesfully for several years now.

# Using

Use [Visual Studio Code](https://code.visualstudio.com/) with the [Platform IO IDE extension](https://marketplace.visualstudio.com/items?itemName=platformio.platformio-ide). 

To build simply do `Platform IO > Project Tasks > Build`.
As a result of compilation you get the `firmware.bin` file that can be used to flash the ESP8266 device.

```
Linking .pio\build\esp12e\firmware.elf
Building .pio\build\esp12e\firmware.bin
Retrieving maximum program size .pio\build\esp12e\firmware.elf
Checking size .pio\build\esp12e\firmware.elf
Advanced Memory Usage is available via "PlatformIO Home > Project Inspect"
DATA:    [====      ]  43.5% (used 35640 bytes from 81920 bytes)
PROGRAM: [====      ]  37.2% (used 388644 bytes from 1044464 bytes)
```

Alternatively, you can use the Over the Air Updates (OTA) to push never firmware updates.

# Features

* Smart Config
  * With first run (or after a push button is pressed and hold during boot) the device enter config mode.
  * It starts an open WiFi network you can join to from your mobile phone.
  * It displays a configuration website where you can enter basic settings (WiFi name & pass, device name).
  * This uses the WiFiManager library.
* Over the Air Updates (OTA)
  * You can send a command to the device with a URL from which it should download the latest firmware from (bin file).
* Deep sleep mode support (to lower energy usage)
* In `MainApp.cpp` compose what pins are used (ideally in the future this will be configured):

```cpp
    if (_deviceConfig->UniqueId.startsWith("sonoff_") || _deviceConfig->UniqueId.startsWith("switch_")) {
      // Sonoff
      /*
      12 - Connected to RST (deep sleep)
      00 - push button, 0 - pressed, 1 - released
      */

      pushButton->SetPin(0);

      auto sw = new SwitchFeatureController(10, this, 12, true);
      _features.push_back(sw);
      _features.push_back(new PushButtonFeatureController(0, this, pushButton, sw));

    } else if (_deviceConfig->UniqueId.startsWith("sonoff-dual_") || _deviceConfig->UniqueId.startsWith("switch-dual_")) {
      // Sonoff Dual R2
      /*
      05 - Switch 1
      12 - Switch 2
      10 - push button, 0 - pressed, 1 - released
      13 - blue LED
      03 (RX) - DHT22
      */

      Serial.end();

      pushButton->SetPin(10);
      statusLed->SetPin(13);

      auto sw = new SwitchFeatureController(10, this, 5, true);
      _features.push_back(sw);
      _features.push_back(new PushButtonFeatureController(0, this, pushButton, sw));
      _features.push_back(new SwitchFeatureController(11, this, 12, true));
      _features.push_back(new TempFeatureController(30, 31, this, 3));
    }
  }
```
