#include "Arduino.h"
#include "ShiftRegister74HC595.h"

// constructor
ShiftRegister74HC595::ShiftRegister74HC595(int numberOfShiftRegisters, int serialDataPin, int clockPin, int latchPin)
{
    // set attributes
    _numberOfShiftRegisters = numberOfShiftRegisters;

    //Pin connected to SH_CP of 74HC595
    _clockPin = clockPin;
    ////Pin connected to DS of 74HC595
    _serialDataPin = serialDataPin;
    //Pin connected to ST_CP of 74HC595
    _latchPin = latchPin;

    // define pins as outputs
    pinMode(clockPin, OUTPUT);
    pinMode(serialDataPin, OUTPUT);
    pinMode(latchPin, OUTPUT);

    // set pins low
    digitalWrite(clockPin, LOW);
    digitalWrite(serialDataPin, LOW);
    digitalWrite(latchPin, LOW);
}

void ShiftRegister74HC595::setAll() {
    // ground latchPin and hold low for as long as you are transmitting
    digitalWrite(_latchPin, LOW);

    for (auto i = _numberOfShiftRegisters - 1; i >= 0; i--) {
        // See https://www.arduino.cc/en/Reference/ShiftOut
        shiftOut(_serialDataPin, _clockPin, MSBFIRST, _digitalValues[i]);
    }

    // return the latch pin high to signal chip that it
    // no longer needs to listen for information
    digitalWrite(_latchPin, HIGH);
    delay(200);
    digitalWrite(_latchPin, LOW);

    //digitalWrite(_latchPin, HIGH);
    //digitalWrite(_latchPin, LOW);
}

uint8_t* ShiftRegister74HC595::getAll() {
    return _digitalValues;
}

void ShiftRegister74HC595::set(int pin, uint8_t value) {
    if (value == 1)
        _digitalValues[pin / 8] |= 1 << (pin % 8);
    else
        _digitalValues[pin / 8] &= ~(1 << (pin % 8));

    setAll();
}

uint8_t ShiftRegister74HC595::get(int pin) {
    return (_digitalValues[pin / 8] >> (pin % 8)) & 1;
}

void ShiftRegister74HC595::setAllHigh() {
    for (auto i = 0; i < _numberOfShiftRegisters; i++) {
        _digitalValues[i] = 255;
    }
    setAll();
}

void ShiftRegister74HC595::setAllLow() {
    for (auto i = 0; i < _numberOfShiftRegisters; i++) {
        _digitalValues[i] = 0;
    }
    setAll();
}
