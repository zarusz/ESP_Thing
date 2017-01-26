#include "ColorLEDViaIRDriverFeatureController.h"
#include "SwitchFeatureController.h"
#include "Values.h"

#define NEC_NULL    0x000000
#define NEC_BITS    32

#define NEC_POWER           0xff02fd
#define NEC_INTENSITY_UP    0xff3ac5
#define NEC_INTENSITY_DOWN  0xffba45
#define NEC_W               0xff22dd
#define NEC_R               0xff1ae5
#define NEC_G               0xff9a65
#define NEC_B               0xffa25d

ColorLEDViaIRDriverFeatureController::ColorLEDViaIRDriverFeatureController(int port, DeviceContext* context, int pin)
  : IRFeatureController(port, context, pin)
{
}

void ColorLEDViaIRDriverFeatureController::Handle(const char* topic, const Buffer& payload)
{
  String str;
  payload.ToString(str);

  // handle OnOff
  auto on = str == STATE_ON;
  auto off = str == STATE_OFF;
  if (on || off)
  {
    SendSignal(NEC_POWER, "POWER");
    return;
  }

  // handle HSB
  // see http://docs.openhab.org/concepts/items.html#hsbtype


  sprintf(_logger->Msg(), "[SwitchFeatureController] Bad value '%s' (espected %s or %s)", str.c_str(), STATE_ON, STATE_OFF);
  _logger->Log(Debug);
}

void ColorLEDViaIRDriverFeatureController::SendSignal(int value, const char* command)
{
  sprintf(_logger->Msg(), "[ColorLEDViaIRDriverFeatureController] Port %d: Sending command %s - IR: (format: NEC, bits: %d, data: %s)\n", _port, command, NEC_BITS, String(value, HEX).c_str());
  _logger->Log(Debug);

  _irsend.sendNEC(value, NEC_BITS);
  _irsend.sendNEC(NEC_NULL, 0);
}
