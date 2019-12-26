#ifndef _Pins_h
#define _Pins_h

enum PinMode
{
    Output,
    Input
};

class Pins
{
public:
  virtual void SetMode(int pin, PinMode mode);
  virtual void SetValue(int pin, bool high);
  virtual bool isValueHigh(int pin);
};

#endif
