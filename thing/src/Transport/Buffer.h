#ifndef _Buffer_h
#define _Buffer_h

#include <Arduino.h>

class Buffer
{
protected:
  uint _size;
  uint _capacity;

public:
  Buffer(uint capacity)
  {
    _capacity = capacity;
    _size = 0;
  }

  virtual byte* Data() = 0;
  virtual const byte* Data() const = 0;

  uint MaxSize() const { return _capacity; }
  uint Size() const { return _size; }
  void Reset() { Resize(0); }

  bool Resize(int newSize)
  {
    // ensure 1 extra caracter can still be maintained
    if (newSize + 1 > _capacity)
      return false;

    _size = newSize;
    // always have zero byte as the last element past size
    Data()[_size] = 0;

    return true;
  }

  bool ToString(String &s) const
  {
      s = (const char*) Data();
      return true;
  }

  bool ToString(char* s, int maxSize) const
  {
      auto size = Size();
      if (size + 1 > maxSize)
        return false;

      memcpy(s, Data(), size);
      s[size] = 0;
      return true;
  }
};

template <uint Capacity>
class StaticBuffer : public Buffer
{
protected:
  byte _data[Capacity];

public:
  StaticBuffer() : Buffer(Capacity) {}
  virtual byte* Data() { return (byte*) &_data; }
  virtual const byte* Data() const { return (const byte*) &_data; }
};
/*
class StringBuffer : public Buffer
{
protected:
  const char* _string;

public:
  StringBuffer(const char* string) : Buffer(strlen(string)), _string(string) { Resize(_capacity); }
  virtual byte* Data() { return NULL; }
  virtual const byte* Data() const { return (const byte*) _string; }
};
*/

#endif
