#pragma once

enum PinMode
{
  Output,
  Input
};

class Pins {
public:
  virtual void SetMode(int pin, PinMode mode);
  virtual void SetValue(int pin, bool high);
  virtual bool isValueHigh(int pin);
};
