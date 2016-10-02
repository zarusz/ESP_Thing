#ifndef _Serializer_h
#define _Serializer_h

#include "Buffer.h"

class Serializer
{
public:
  virtual bool Decode(const Buffer& payload, void* msg) const = 0;
  virtual bool Encode(const void* msg, Buffer*& payload) = 0;
};

#endif
