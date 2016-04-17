#ifndef _CommHub_h
#define _CommHub_h

#include <Arduino.h>
#include <pb.h>

class CommHub
{
	virtual bool PublishMessage(const char* topic, const pb_field_t* msg_fields, const void* msg) = 0;
};

#endif
