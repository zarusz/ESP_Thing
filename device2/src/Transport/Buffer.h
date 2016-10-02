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
  void Reset() { _size = 0; }

  bool Resize(int newSize)
  {
    if (newSize > _capacity)
      return false;

    _size = newSize;
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

#endif
