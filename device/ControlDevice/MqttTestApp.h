// MqttTestApp.h

#ifndef _MQTTTESTAPP_h
#define _MQTTTESTAPP_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

class MqttTestApp {

public:
	void Init();
	void Loop();
};


#endif

