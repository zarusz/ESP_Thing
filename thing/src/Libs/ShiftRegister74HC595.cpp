#include "Arduino.h"
#include "ShiftRegister74HC595.h"

// constructor
ShiftRegister74HC595::ShiftRegister74HC595(int numberOfShiftRegisters, int serialDataPin, int clockPin, int latchPin)
{
    // set attributes
    _numberOfShiftRegisters = numberOfShiftRegisters;

    // pin connected to SH_CP of 74HC595
    _clockPin = clockPin;
    // pin connected to DS of 74HC595
    _serialDataPin = serialDataPin;
    // pin connected to ST_CP of 74HC595
    _latchPin = latchPin;

    // define pins as outputs
    pinMode(clockPin, OUTPUT);
    pinMode(serialDataPin, OUTPUT);
    pinMode(latchPin, OUTPUT);

    // set pins low
    //digitalWrite(clockPin, LOW);
    //digitalWrite(serialDataPin, LOW);
    digitalWrite(latchPin, HIGH);
}

void ShiftRegister74HC595::SetAll()
{
    // ground latchPin and hold low for as long as you are transmitting
    digitalWrite(_latchPin, LOW);

    for (auto i = _numberOfShiftRegisters - 1; i >= 0; i--)
    {
        // See https://www.arduino.cc/en/Reference/ShiftOut
        shiftOut(_serialDataPin, _clockPin, MSBFIRST, _digitalValues[i]);
    }

    // return the latch pin high to signal chip that it
    // no longer needs to listen for information
    digitalWrite(_latchPin, HIGH);
}

uint8_t* ShiftRegister74HC595::GetAll()
{
    return _digitalValues;
}

void ShiftRegister74HC595::Set(int pin, uint8_t value)
{
    if (value == 1)
        _digitalValues[pin / 8] |= 1 << (pin % 8);
    else
        _digitalValues[pin / 8] &= ~(1 << (pin % 8));

    SetAll();
}

uint8_t ShiftRegister74HC595::Get(int pin)
{
    return (_digitalValues[pin / 8] >> (pin % 8)) & 1;
}

void ShiftRegister74HC595::SetAllHigh()
{
    for (auto i = 0; i < _numberOfShiftRegisters; i++) {
        _digitalValues[i] = 255;
    }
    SetAll();
}

void ShiftRegister74HC595::SetAllLow()
{
    for (auto i = 0; i < _numberOfShiftRegisters; i++) {
        _digitalValues[i] = 0;
    }
    SetAll();
}
