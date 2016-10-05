#include <Arduino.h>
#include "MainApp.h"

// Update these with values suitable for your network.

MainApp mainApp;

void setup()
{
	mainApp.Init();
}

void loop()
{
	mainApp.Loop();
}
