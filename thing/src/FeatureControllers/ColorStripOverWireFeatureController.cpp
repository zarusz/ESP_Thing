#include "ColorStripOverWireFeatureController.h"
#include "..\Utils\Colors.h"
#include "Utils/TimeUtil.h"
#include "Values.h"
#include <Wire.h>

ColorStripOverWireFeatureController::ColorStripOverWireFeatureController(
    int port, DeviceContext *context, int address, int index)
    : FeatureController(port, FeatureType::IR, context) {
  _address = address;
  _index = index;

  _lastUpdateMs = 0;
  _updateIntervalMs = 5000;
}

void ColorStripOverWireFeatureController::SetState(bool on) {
  SetState(on, _h, _s, _v);
}

void ColorStripOverWireFeatureController::SetState(bool on, float h, float s,
                                                   float v) {
  _on = on;
  _h = h;
  _s = s;
  _v = v;

  if (on) {
    HSV hsv = {.h = h, .s = s / 100.0f, .v = v / 100.0f};

    _rgb = Colors::hsv2rgb(hsv);

    _r = round(_rgb.r * 255);
    _g = round(_rgb.g * 255);
    _b = round(_rgb.b * 255);
  } else {
    _r = 0;
    _g = 0;
    _b = 0;
  }

  sprintf(_logger->Msg(),
          "[ColorStripOverWireFeatureController] Color RGB = %d %d %d", _r, _g,
          _b);
  _logger->Log(Debug);

  if (UpdateSlave() != 0) {
    sprintf(_logger->Msg(), "[ColorStripOverWireFeatureController] "
                            "Communication problem with slave");
    _logger->Log(Error);
  }
}

void ColorStripOverWireFeatureController::Start() {
  SetState(false);
}

void ColorStripOverWireFeatureController::Stop() {
  SetState(false);
}

void ColorStripOverWireFeatureController::Handle(const char *topic,
                                                 const Buffer &payload) {
  String str;
  payload.ToString(str);

  if (str == STATE_OFF) {
    SetState(false);
  } else if (str == STATE_ON) {
    SetState(true);
  } else {
    // [0-360],[0-100],[0-100]
    // H,S,V
    auto cstr = str.c_str();
    auto c1 = str.indexOf(",") + 1;
    auto c2 = str.indexOf(",", c1) + 1;

    auto h = atof(cstr);
    auto s = atof(cstr + c1);
    auto v = atof(cstr + c2);

    SetState(true, h, s, v);
  }
}

int ColorStripOverWireFeatureController::UpdateSlave() {
  int options = 1 << _index;
  // Serial.print("Options: ");
  // Serial.println(options);

  Wire.beginTransmission(8);         // transmit to device #8
  Wire.write(_r);                    // sends five bytes
  Wire.write(_g);                    // sends one byte
  Wire.write(_b);                    // sends one byte
  Wire.write(options);               // sends one byte
  auto err = Wire.endTransmission(); // stop transmitting

  /*
  err will be one of:
   0: success
   2: address NAK, No slave answered
   3: data NAK, Slave returned NAK, did not acknowledge reception of a byte
   4: other error, here is were The Arduino Library sticks any other error.
       like bus_arbitration_lost,
  The actual error codes that the TWI hardware can express is found here
  C:\Program Files\Arduino\hardware\tools\avr\avr\include\util\twi.h

  They are not returned to the user, all are lumped into err=4.  I modified my
  Wire.h to return them with a Wire.lastError() call
  */

  sprintf(_logger->Msg(),
          "[ColorStripOverWireFeatureController] Slave response: %d", err);
  _logger->Log(Debug);

  delay(50);
  return err;
}

void ColorStripOverWireFeatureController::Loop() {
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  // ToDo
}
