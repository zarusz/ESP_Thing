#include "PbSerializer.h"

#include <pb_encode.h>
#include <pb_decode.h>

PbMessage::PbMessage(const pb_field_t* fields, void* message)
{
  Fields = fields;
  Message = message;
}

PbSerializer::PbSerializer()
{
}

bool PbSerializer::Decode(const Buffer& payload, void* msg) const
{
  auto message = static_cast<PbMessage*>(msg);

   /* Create a stream that reads from the buffer. */
  pb_istream_t stream = pb_istream_from_buffer(payload.Data(), payload.Size());
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

bool PbSerializer::Encode(const void* msg, Buffer*& payload)
{
  auto message = static_cast<const PbMessage*>(msg);

  _buffer.Reset();

  /* Create a stream that will write to our buffer. */
  pb_ostream_t stream = pb_ostream_from_buffer(_buffer.Data(), _buffer.MaxSize());
  /* Now we are ready to encode the message! */
  bool status = pb_encode(&stream, message->Fields, message->Message);
  _buffer.Resize(stream.bytes_written);

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
