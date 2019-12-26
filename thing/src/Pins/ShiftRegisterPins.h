#pragma once

#include "../Libs/ShiftRegister74HC595.h"
#include "Pins.h"

class ShiftRegisterPins : public Pins {
private:
  ShiftRegister74HC595 _shiftRegister;
  int _virtualPinStart;

public:
  ShiftRegisterPins(int serialDataPin, int clockPin, int latchPin,
                    int virtualPinStart);

  virtual void SetMode(int pin, PinMode mode);
  virtual void SetValue(int pin, bool high);
  virtual bool IsValueHigh(int pin);
};
