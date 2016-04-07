//
//
//

#include "MainApp.h"

MainApp::MainApp(const char* newtworkSsid, const char* networkPassword, const char* mqttServer, const char* nodeId, MQTT_CALLBACK_SIGNATURE)
	: pubSubClient(espClient)
{
	this->networkSsid = newtworkSsid;
	this->networkPassword = networkPassword;
	this->mqttServer = mqttServer;
	this->nodeId = nodeId;

	pubSubClient.setServer(mqttServer, 1883);
	pubSubClient.setCallback(callback);
}

MainApp::~MainApp()
{
}

void MainApp::Init()
{
	pinMode(BUILTIN_LED, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
	Serial.begin(115200);

	SetupWifi();
}

void MainApp::Loop()
{
	if (!pubSubClient.connected()) {
		ReconnectPubSub();
	}
	pubSubClient.loop();

	long now = millis();
	if (now - lastMsg > 2000) {
		lastMsg = now;
		++value;
		snprintf(msg, 75, "hello world #%ld", value);
		Serial.print("Publish message: ");
		Serial.println(msg);

		pubSubClient.publish("outTopic", msg);
	}
}

void MainApp::SetupWifi()
{
	delay(10);
	// We start by connecting to a WiFi network
	Serial.println();
	Serial.print("Connecting to ");
	Serial.println(this->networkSsid);

	WiFi.begin(networkSsid, networkPassword);
	while (WiFi.status() != WL_CONNECTED) {
		delay(500);
		Serial.print(".");
	}

	Serial.println("");
	Serial.println("WiFi connected");
	Serial.println("IP address: ");
	Serial.println(WiFi.localIP());
}

void MainApp::ReconnectPubSub()
{
	// Loop until we're reconnected
	while (!pubSubClient.connected())
	{
		Serial.print("Attempting MQTT connection...");
		// Attempt to connect
		if (pubSubClient.connect(nodeId))
		{
			Serial.println("connected");
			// Once connected, publish an announcement...
			pubSubClient.publish("outTopic", "hello world");
			// ... and resubscribe
			pubSubClient.subscribe("inTopic");
		}
		else
		{
			Serial.print("failed, rc=");
			Serial.print(pubSubClient.state());
			Serial.println(" try again in 5 seconds");
			// Wait 5 seconds before retrying
			delay(5000);
		}
	}
}

void MainApp::Callback(char* topic, byte* payload, unsigned int length)
{
	Serial.print("Message arrived [");
	Serial.print(topic);
	Serial.print("] ");
	for (int i = 0; i < length; i++)
	{
		Serial.print((char)payload[i]);
	}
	Serial.println();

	// Switch on the LED if an 1 was received as first character
	if ((char)payload[0] == '1')
	{
		digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
										  // but actually the LED is on; this is because
										  // it is acive low on the ESP-01)
	}
	else
	{
		digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
	}
}
