#ifndef _PbSerializer_h
#define _PbSerializer_h

#include <pb.h>

#include "Buffer.h"
#include "Serializer.h"

class PbSerializer : public Serializer
{
protected:
  StaticBuffer<256> _buffer;

public:
  PbSerializer();

  virtual bool Decode(const Buffer& payload, void* msg) const;
  virtual bool Encode(const void* msg, Buffer*& payload);
};

class PbMessage
{
public:
  const pb_field_t* Fields;
  void* Message;

public:
  PbMessage(const pb_field_t* fields, void* message);
};

#endif
