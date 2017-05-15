/*
  ShiftRegister74HC595.h - Library for easy control of the 74HC595 shift register.
  Created by Timo Denk (www.simsso.de), Nov 2014.
  Additional information are available on http://shiftregister.simsso.de/
  Released into the public domain.
*/

#ifndef ShiftRegister74HC595_h
#define ShiftRegister74HC595_h

#include "Arduino.h"

#define MAX_REGISTERS   4

class ShiftRegister74HC595
{
public:
    ShiftRegister74HC595(int numberOfShiftRegisters, int serialDataPin, int clockPin, int latchPin);
    void Set(int pin, uint8_t value);
    uint8_t Get(int pin);

    void SetAllLow();
    void SetAllHigh();

private:
    int _clockPin;
    int _serialDataPin;
    int _latchPin;
    int _numberOfShiftRegisters;
    uint8_t _digitalValues[MAX_REGISTERS];

    void SetAll();
    uint8_t* GetAll();
};

#endif
