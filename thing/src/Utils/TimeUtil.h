#ifndef _TimeUtil_h
#define _TimeUtil_h

#include <Arduino.h>

class TimeUtil
{
public:
  static ulong IntervalStart()
  {
      return millis();
  }

  static bool IntervalPassed(ulong& lastTimeMs, uint intervalMs)
  {
    if (IsIntervalPassed(lastTimeMs, intervalMs))
    {
      lastTimeMs = IntervalStart();
      return true;
    }
    return false;
  }

  static bool IsIntervalPassed(ulong& lastTimeMs, uint intervalMs)
  {
    auto now = IntervalStart();
    if (now > lastTimeMs + intervalMs)
    {
      return true;
    }
    return false;
  }

};

#endif
