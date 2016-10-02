#include "TempFeatureController.h"
#include "Utils/TimeUtil.h"

TempFeatureController::TempFeatureController(int port, int portForHumidity, DeviceContext* context, int pin, const char* topic)
  : FeatureController(port, FeatureType::FeatureType_SENSOR_TEMPERATURE, context),
    _dht(pin, DHT22)
{
  _topic = topic;
  _portForHumidity = portForHumidity;
  _pin = pin;

  _lastTemp = false;
  _lastUpdateMs = 0;
  _updateIntervalMs = 10000;

  _dht.begin();
}

TempFeatureController::~TempFeatureController()
{
}

void TempFeatureController::Loop()
{
  if (!TimeUtil::IntervalPassed(_lastUpdateMs, _updateIntervalMs))
  {
    return;
  }

  DeviceEvents events = DeviceEvents_init_zero;

  if (_lastTemp)
  {
    // Reading temperature or humidity takes about 250 milliseconds!
    // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
    auto h = _dht.readHumidity();

    if (!isnan(h))
    {
      Serial.printf("[DHT22] The humidity is %d\n", (int) h);

      events.has_humidityMeasureEvent = true;
      strcpy(events.humidityMeasureEvent.device_id, _context->GetConfig().uniqueId);
      events.humidityMeasureEvent.port = _portForHumidity;
      events.humidityMeasureEvent.value = h;
    }
  }
  else
  {
    // Read temperature as Celsius (the default)
    auto t = _dht.readTemperature();

    if (!isnan(t))
    {
      Serial.printf("[DHT22] The temperature is %d\n", (int) t);

      events.has_temperatureMeasureEvent = true;
      strcpy(events.temperatureMeasureEvent.device_id, _context->GetConfig().uniqueId);
      events.temperatureMeasureEvent.port = _port;
      events.temperatureMeasureEvent.value = t;
    }
  }

  if (events.has_humidityMeasureEvent || events.has_temperatureMeasureEvent)
  {
    PbMessage message(DeviceEvents_fields, &events);
    _context->GetMessageBus()->Publish(_topic, &message);
  }
  else
  {
    // TODO: log error
    Serial.println("[DHT22] Error: Cannot read the temperature/humidity sensor.");
  }

  _lastTemp = !_lastTemp;
}
