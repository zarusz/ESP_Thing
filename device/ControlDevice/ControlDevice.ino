
/*
 Name:		ControlDevice.ino
 Created:	3/13/2016 10:51:45 AM
 Author:	Tomasz
*/

// the setup function runs once when you press reset or power the board
#include <SoftwareSerial.h>  //dolaczenie biblioteki do obsługi programowego interfejsu UART
#include "WiFi.h"

SoftwareSerial esp01(2, 3); // Inicjalizacja programowego interfejsu UART: 2-RX (polaczyc z Tx modulu ESP); 3-TX (polaczyc z Rx modulu ESP)
WiFiDevice wifiDevice(&esp01);


void setup() {

}

// the loop function runs over and over again until power down or reset
void loop() {
  
}
