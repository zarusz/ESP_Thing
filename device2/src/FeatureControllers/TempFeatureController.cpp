#include "TempFeatureController.h"

TempFeatureController::TempFeatureController(int port, int portForHumidity, DeviceContext* context, int pin)
  : FeatureController(port, context), dht(pin, DHT22)
{
  this->portForHumidity = portForHumidity;
  this->pin = pin;
  lastMsgMs = 0;
  updateIntervalMs = 30000;
  dht.begin();
}

TempFeatureController::~TempFeatureController()
{
}

void TempFeatureController::Handle(DeviceMessage& deviceMessage)
{
}

void TempFeatureController::Loop()
{
  auto now = millis();
  if (now - lastMsgMs > updateIntervalMs) {
    lastMsgMs = now;

    // Reading temperature or humidity takes about 250 milliseconds!
    // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
    float h = dht.readHumidity();

    // Read temperature as Celsius (the default)
    float t = dht.readTemperature();

    DeviceEvents events = DeviceEvents_init_zero;

    if (!isnan(t))
    {
      Serial.println(String("The temperature is ") + t);

      events.has_temperatureMeasureEvent = true;
      strcpy(events.temperatureMeasureEvent.device_id, context->GetConfig().uniqueId);
      events.temperatureMeasureEvent.port = port;
      events.temperatureMeasureEvent.value = t;
    }

    if (!isnan(h))
    {
      Serial.println(String("The humidity is ") + h);

      events.has_humidityMeasureEvent = true;
      strcpy(events.humidityMeasureEvent.device_id, context->GetConfig().uniqueId);
      events.humidityMeasureEvent.port = portForHumidity;
      events.humidityMeasureEvent.value = h;
    }

    if (events.has_humidityMeasureEvent || events.has_temperatureMeasureEvent)
    {
      context->GetCommHub().PublishMessage(EVENTS_TOPIC, DeviceEvents_fields, &events);
    }

    if (isnan(h) || isnan(t))
    {
        // TODO: log error
        Serial.println("Error: Cannot read the temp/humidity sensor. ");
    }
  }
}
