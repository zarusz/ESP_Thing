#include "PbSerializer.h"

#include <pb_encode.h>
#include <pb_decode.h>

PbMessage::PbMessage(const pb_field_t* fields, void* message)
{
  Fields = fields;
  Message = message;
}

PbSerializer::PbSerializer()
  : _buffer(255)
{
}

bool PbSerializer::Decode(const std::vector<byte>& payload, void* msg) const
{
  auto message = static_cast<PbMessage*>(msg);

   /* Create a stream that reads from the buffer. */
  pb_istream_t stream = pb_istream_from_buffer(payload.data(), payload.size());
  /* Now we are ready to decode the message. */
  bool status = pb_decode(&stream, message->Fields, message->Message);

  /* Check for errors... */
  if (!status)
  {
      Serial.print("[PbSerializer] Decoding failed: ");
      Serial.println(PB_GET_ERROR(&stream));
      return false;
  }
  return true;
}

bool PbSerializer::Encode(const void* msg, std::vector<byte>*& payload)
{
  auto message = static_cast<const PbMessage*>(msg);

  _buffer.resize(_buffer.capacity());

  /* Create a stream that will write to our buffer. */
  pb_ostream_t stream = pb_ostream_from_buffer(_buffer.data(), _buffer.capacity());
  /* Now we are ready to encode the message! */
  bool status = pb_encode(&stream, message->Fields, message->Message);
  _buffer.resize(stream.bytes_written);

  /* Then just check for any errors.. */
  if (!status)
  {
    Serial.print("[PbSerializer] Encoding failed: ");
    Serial.println(PB_GET_ERROR(&stream));
    return false;
  }

  payload = &_buffer;
  return true;
}
