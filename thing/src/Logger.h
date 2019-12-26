#pragma once

enum LogLevel
{
  Debug = 0,
  Info = 1,
  Warn = 2,
  Error = 3
};

class Logger {
protected:
  char _logBuffer[128];

public:
  // virtual void Log(LogLevel level, const char* format, ...) = 0;
  virtual char *Msg() {
    return _logBuffer;
  }
  virtual void Log(LogLevel level, const char *msg = 0) = 0;
};
