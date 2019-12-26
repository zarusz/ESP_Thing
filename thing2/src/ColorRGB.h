#ifndef _ColorRGB_h
#define _ColorRGB_h

class ColorRGB
{
private:
  int _r, _g, _b;
  int _pinR, _pinG, _pinB;

public:
  ColorRGB(int pinR, int pinG, int pinB);
  void SetColor(int r, int g, int b);
};

#endif
