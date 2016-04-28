#include "ShiftRegisterPins.h"

ShiftRegisterPins::ShiftRegisterPins(int serialDataPin, int clockPin, int latchPin, int virtualPinStart)
  : shiftRegister(1, serialDataPin, clockPin, latchPin)
{
  this->virtualPinStart = virtualPinStart;
}

void ShiftRegisterPins::SetMode(int pin, PinMode mode)
{
  if (pin < virtualPinStart)
  {
      Pins::SetMode(pin, mode);
  }
}

void ShiftRegisterPins::SetValue(int pin, bool high)
{
  if (pin >= virtualPinStart)
  {
    shiftRegister.set(pin - virtualPinStart, high ? 1 : 0);
  }
  else
  {
    Pins::SetValue(pin, high);
  }
}

bool ShiftRegisterPins::isValueHigh(int pin)
{
  if (pin < virtualPinStart)
  {
    return Pins::isValueHigh(pin);
  }
  return false;
}
