#include "ColorRGB.h"
#include "Arduino.h"

ColorRGB::ColorRGB(int pinR, int pinG, int pinB)
{
  _pinR = pinR;
  _pinG = pinG;
  _pinB = pinB;

  pinMode(_pinR, OUTPUT);
  pinMode(_pinG, OUTPUT);
  pinMode(_pinB, OUTPUT);
}

void ColorRGB::SetColor(int r, int g, int b)
{
  _r = r;
  _g = g;
  _b = b;

  analogWrite(_pinR, _r);
  analogWrite(_pinG, _g);
  analogWrite(_pinB, _b);
}
