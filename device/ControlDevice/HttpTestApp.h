// HttpTestApp.h

#ifndef _HTTPTESTAPP_h
#define _HTTPTESTAPP_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#include <ESP8266WiFiMulti.h>

class HttpTestApp {

private:
	ESP8266WiFiMulti WiFiMulti;
public:
	void Init();
	void Loop();

};

#endif

