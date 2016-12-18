#include <Arduino.h>
#include "MainApp.h"

MainApp mainApp;

void setup()
{
	mainApp.Init();
}

void loop()
{
	mainApp.Loop();
}
