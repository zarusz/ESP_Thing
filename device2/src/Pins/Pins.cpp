#include <Arduino.h>
#include "../Libs/ShiftRegister74HC595.h"
#include "Pins.h"

void Pins::SetMode(int pin, PinMode mode)
{
  pinMode(pin, mode == PinMode::Output ? OUTPUT : INPUT);
}

void Pins::SetValue(int pin, bool high)
{
  digitalWrite(pin, high ? HIGH : LOW);
}

bool Pins::isValueHigh(int pin)
{
  digitalRead(pin) == HIGH;
}