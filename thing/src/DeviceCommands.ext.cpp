#include "DeviceCommands.ext.h"

const char* EnumLabel(IRFormat format)
{
  switch (format) {
    case IRFormat_NEC:
      return "NEC";
    case IRFormat_SONY:
      return "SONY";
  }
  return "Unknown";
}
