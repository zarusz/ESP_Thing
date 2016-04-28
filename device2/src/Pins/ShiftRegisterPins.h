#ifndef _ShiftRegisterPins_h
#define _ShiftRegisterPins_h

#include "Pins.h"
#include "../Libs/ShiftRegister74HC595.h"

class ShiftRegisterPins : public Pins
{
private:
  ShiftRegister74HC595 shiftRegister;
  int virtualPinStart;

public:
  ShiftRegisterPins(int serialDataPin, int clockPin, int latchPin, int virtualPinStart);

  virtual void SetMode(int pin, PinMode mode);
  virtual void SetValue(int pin, bool high);
  virtual bool isValueHigh(int pin);
};

#endif
