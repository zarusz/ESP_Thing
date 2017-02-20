#include "ColorStripFeatureController.h"
#include "..\Utils\Colors.h"
#include "Values.h"

ColorStripFeatureController::ColorStripFeatureController(int port, DeviceContext* context, int pinR, int pinG, int pinB)
  : FeatureController(port, FeatureType::FeatureType_IR, context)
{
  _pinR = pinR;
  _pinG = pinG;
  _pinB = pinB;

  _on = false;
  SetHSV(0, 0, 0);
}

void ColorStripFeatureController::SetHSV(float h, float s, float v)
{
  _h = h;
  _s = s;
  _v = v;

  HSV hsv = {
      .h = h,
      .s = s / 100.0f,
      .v = v / 100.0f
  };

  _rgb = Colors::hsv2rgb(hsv);
}

void ColorStripFeatureController::Start()
{
  pinMode(_pinR, OUTPUT);
  pinMode(_pinG, OUTPUT);
  pinMode(_pinB, OUTPUT);
}

void ColorStripFeatureController::Handle(const char* topic, const Buffer& payload)
{
  String str;
  payload.ToString(str);

  int pwm_r = 0;
  int pwm_g = 0;
  int pwm_b = 0;

  if (str == STATE_OFF) {
    _on = false;
  } else {
    if (str == STATE_ON) {
      _on = true;
    } else {
      // [0-360],[0-100],[0-100]
      // H,S,V
      auto cstr = str.c_str();
      auto c1 = str.indexOf(",") + 1;
      auto c2 = str.indexOf(",", c1) + 1;

      auto h = atof(cstr);
      auto s = atof(cstr + c1);
      auto v = atof(cstr + c2);

      //sprintf(_logger->Msg(), "c1=%d, c2=%d, h=%d,s=%d,v=%d", c1, c2, (int) h, (int) s, (int) v);
      //_logger->Log(Debug);

      // 0,0,100 - biały
      // 0,0,0 - czarny (off)
      SetHSV(h, s, v);
    }
    pwm_r = round(_rgb.r * PWMRANGE);
    pwm_g = round(_rgb.g * PWMRANGE);
    pwm_b = round(_rgb.b * PWMRANGE);
  }

/*
  auto s = str.c_str();
  auto rgb = strtol(s, NULL, 16);
  int r = (rgb >> 16) & 255;
  int g = (rgb >> 8) & 255;
  int b = rgb & 255;
*/

  analogWrite(_pinR, pwm_r);
  analogWrite(_pinG, pwm_g);
  analogWrite(_pinB, pwm_b);

  // handle HSB
  // see http://docs.openhab.org/concepts/items.html#hsbtype

  sprintf(_logger->Msg(), "[ColorStripFeatureController] Msg %s Color RGB = %d %d %d", str.c_str(), pwm_r, pwm_g, pwm_b);
  _logger->Log(Debug);
}
