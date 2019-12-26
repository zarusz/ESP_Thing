#pragma once

#include "../DeviceContext.h"
#include "../Transport/Buffer.h"

/* Enum definitions */
typedef enum _FeatureType {
  SWITCH = 0,
  IR = 1,
  SENSOR_IR = 2,
  SENSOR_TEMPERATURE = 3,
  SENSOR_HUMIDITY = 4,
  SENSOR_MOTION = 5
} FeatureType;

typedef enum _IRFormat {
  IRFormat_Unknown = 0,
  IRFormat_NEC = 1,
  IRFormat_SONY = 2
} IRFormat;

class FeatureController {
protected:
  int _port;
  FeatureType _type;
  DeviceContext *_context;
  Logger *_logger;

public:
  FeatureController(int port, FeatureType type, DeviceContext *context);
  virtual ~FeatureController();

  virtual void Start();
  virtual void Stop();

  // virtual uint Describe(DevicePort* ports);
  virtual void Loop();

  virtual bool TryHandle(const char *path, const Buffer &payload);
  virtual bool CanHandle(const char *path, const Buffer &payload);

protected:
  virtual void Handle(const char *path, const Buffer &payload);
  virtual void PublishState(const String &payload, int port = 0);
};
