#include "ShiftRegisterPins.h"

ShiftRegisterPins::ShiftRegisterPins(int serialDataPin, int clockPin,
                                     int latchPin, int virtualPinStart)
    : _shiftRegister(1, serialDataPin, clockPin, latchPin),
      _virtualPinStart(virtualPinStart) {
  _shiftRegister.SetAllHigh();
}

void ShiftRegisterPins::SetMode(int pin, PinMode mode) {
  if (pin < _virtualPinStart) {
    Pins::SetMode(pin, mode);
  }
}

void ShiftRegisterPins::SetValue(int pin, bool high) {
  if (pin >= _virtualPinStart) {
    _shiftRegister.Set(pin - _virtualPinStart, high ? 1 : 0);
  } else {
    Pins::SetValue(pin, high);
  }
}

bool ShiftRegisterPins::IsValueHigh(int pin) {
  if (pin < _virtualPinStart) {
    return Pins::isValueHigh(pin);
  }
  return false;
}
