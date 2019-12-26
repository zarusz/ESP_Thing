/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include <Arduino.h>
#include <Wire.h>
#include "ColorRGB.h"

// See https://www.arduino.cc/en/Tutorial/MasterWriter

#define   WIRE_ADDRESS  8

ColorRGB color1(10, 11, 9);
ColorRGB color2( 5,  3, 6);

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany)
{
  auto avail = Wire.available();
  Serial.print("Awailable bytes: ");
  Serial.println(avail);

  if (avail != 4 && avail != 3) {
    return;
  }

  int r = Wire.read();
  int g = Wire.read();
  int b = Wire.read();
  int options = avail == 4 ? Wire.read() : 255;

  Serial.print(" R: ");
  Serial.print(r);
  Serial.print(" G: ");
  Serial.print(g);
  Serial.print(" B: ");
  Serial.print(b);
  Serial.print(" Options: ");
  Serial.println(options);

  if (options & 1) {
    Serial.println("Setting LED1");
    color1.SetColor(r, g, b);
  }
  if (options & 2) {
    Serial.println("Setting LED2");
    color2.SetColor(r, g, b);
  }
}

// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
void requestEvent() {
  Wire.write("hello "); // respond with message of 6 bytes
  // as expected by master
}

void setup()
{
  Serial.begin(115000);         // start serial for output
  Serial.println("Ready...");

  Wire.begin(WIRE_ADDRESS);     // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
}

void loop()
{
  delay(100);
}
