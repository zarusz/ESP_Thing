#ifndef _PbSerializer_h
#define _PbSerializer_h

#include <Arduino.h>
#include <pb.h>
#include <vector>

class Serializer
{
public:
  virtual bool Decode(const std::vector<byte>& payload, void* msg) const = 0;
  virtual bool Encode(const void* msg, std::vector<byte>*& payload) = 0;
};

class PbSerializer : public Serializer
{
protected:
  std::vector<byte> _buffer;

public:
  PbSerializer();

  virtual bool Decode(const std::vector<byte>& payload, void* msg) const;
  virtual bool Encode(const void* msg, std::vector<byte>*& payload);
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
