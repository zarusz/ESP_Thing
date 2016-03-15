// 
// 
// 

#include "WiFi.h"


WiFiDevice::WiFiDevice(SoftwareSerial* serial) {
	this->serial = serial;
}

WiFiDevice::~WiFiDevice() {
	// reset the module, so that it does not broadcast or connects to a network
	this->Reset();
}

bool WiFiDevice::ConnectToNetwork(String ssid, String password)
{
	Reset();
	// OK as response
	SendCommand("AT+CWMODE=2", 1000);         //ustawienie w trybie Access Point
	// OK as response
	SendCommand("AT+CWJAP=\"" + ssid + "\",\"" + password + "\"", 3000); // connect to the network
	// ip as response
	this->ip = SendCommand("AT+CIFSR", 1000); // uzyskanie adresu IP
	
	// OK as response
	SendCommand("AT+CIPMUX=1", 1000);         //włączenie trybu połączeń wielokrotnych
	return true;
}

bool WiFiDevice::StartNetwork(String ssid, String password)
{
	SendCommand("AT+CWMODE=2", 1000);         //ustawienie w trybie Access Point
	SendCommand("AT+CWSAP=\"" + ssid + "\",\"" + password + "\",3,0", 3000);
	SendCommand("AT+CIFSR", 1000);            //uzyskanie adresu IP (domyślnie 192.168.4.1)
	SendCommand("AT+CIPMUX=1", 1000);         //włączenie trybu połączeń wielokrotnych
	SendCommand("AT+CIPSERVER=1,80", 1000);   //ustawienie serwera na porcie 80
	return false;
}

bool WiFiDevice::Send(String host, int port, byte* content, int contentLength, byte* responseContent, int& maxResponseContent)
{
	// OK
	SendCommand("AT+CIPSTART=4,\"TCP\",\"" + host + "\"," + port, 3000);
	if (!serial->find("OK")) {
		return false;
	}	
	
	// >
	SendCommand("AT+CIPSEND=4," + contentLength, 2000);
	if (!serial->find(">")) {
		return false;
	}

	// SEND OK
	this->serial->write(content, contentLength);
	if (!serial->find("SEND OK")) {
		return false;
	}

	// TODO handle response

	return false;
}

void WiFiDevice::Reset() {
	SendCommand("AT+RST", 1000);
	ip = "";
}

String WiFiDevice::SendCommand(String command, const int timeout)
{
	Serial.println("Sending command: " + command);
	serial->println(command); // wysłanie polecenia do ESP01
	return AwaitResponse(timeout);
}

String WiFiDevice::SendBytes(byte* content, int contentLength, const int timeout)
{
	Serial.println("Sending # of bytes: " + contentLength);
	serial->write(content, contentLength); // wysłanie polecenia do ESP01
	return AwaitResponse(timeout);
}

String WiFiDevice::AwaitResponse(const int timeout) {
	String response = "";

	const long startTime = millis();
	while ((startTime + timeout) > millis())
	{
		delay(50);
		while (serial->available()) //jeśli są jakieś dane z modułu, wtedy następuje ich odczyt
		{
			char c = serial->read(); // odczyt kolejnego znaku
			response += c;
		}
	}
	Serial.println("Got response:" + response);
	return response;
}