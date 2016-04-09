// HttpTestApp.h

#ifndef _HTTPTESTAPP_h
#define _HTTPTESTAPP_h

#include <Arduino.h>
#include <ESP8266WiFiMulti.h>

class HttpTestApp {

private:
	ESP8266WiFiMulti WiFiMulti;
public:
	void Init();
	void Loop();

};

#endif
