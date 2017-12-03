#include "TempFeatureController.h"
#include "Utils/TimeUtil.h"

TempFeatureController::TempFeatureController(int port, int portForHumidity, DeviceContext* context, int pin)
  : FeatureController(port, FeatureType_SENSOR_TEMPERATURE, context),
    _dht(pin, DHT22),
    _portForHumidity(portForHumidity)
{
  _lastTemp = false;
  _lastUpdateMs = 0;
  _updateIntervalMs = 10000;
}

TempFeatureController::~TempFeatureController()
{
}

/*
uint TempFeatureController::Describe(DevicePort* ports)
{
  auto n = FeatureController::Describe(ports);
  ports[n] = (DevicePort) DevicePort_init_zero;
  ports[n].port = _portForHumidity;
  ports[n].feature = FeatureType_SENSOR_HUMIDITY;
  return n + 1;
}
*/

void TempFeatureController::Start()
{
  _dht.begin();
}

void TempFeatureController::Loop()
{
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
    return;

  int port = -1;
  String payload;

  if (_lastTemp)
  {
    // Reading temperature or humidity takes about 250 milliseconds!
    // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
    auto h = _dht.readHumidity();

    if (!isnan(h))
    {
      sprintf(_logger->Msg(), "[DHT22] The humidity is %d", (int) h);
      _logger->Log(Debug);

      port = _portForHumidity;
      payload += h;
    }
  }
  else
  {
    // Read temperature as Celsius (the default)
    auto t = _dht.readTemperature();

    if (!isnan(t))
    {
      sprintf(_logger->Msg(), "[DHT22] The temperature is %d", (int) t);
      _logger->Log(Debug);

      port = _port;
      payload += t;
    }
  }

  if (port != -1)
  {
    PublishState(payload, port);
  }
  else
  {
    _logger->Log(Warn, "[DHT22] Error: Cannot read the temperature/humidity sensor");
  }

  _lastTemp = !_lastTemp;
}
