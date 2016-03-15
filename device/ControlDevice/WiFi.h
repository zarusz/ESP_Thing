// WiFi.h

#ifndef _WIFI_h
#define _WIFI_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif


#include <SoftwareSerial/SoftwareSerial.h>

class WiFiDevice {

protected:
	SoftwareSerial* serial;
	String ip;

	void Reset();
	String SendCommand(String command, const int timeout);
	String SendBytes(byte * content, int contentLength, const int timeout);
	String AwaitResponse(const int timeout);

public:
	WiFiDevice(SoftwareSerial* serial);
	virtual ~WiFiDevice();

	bool ConnectToNetwork(String ssid, String password);
	bool StartNetwork(String ssid, String password);

	bool Send(String ip, int port, byte* content, int contentLength, byte* responseContent, int& maxResponseContent);

};


#endif

