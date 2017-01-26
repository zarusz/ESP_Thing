#ifndef _Colors_h
#define _Colors_h

#include <Arduino.h>
#include <math.h>

// See http://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb-in-range-0-255-for-both

typedef struct {
public:
    float r;       // ∈ [0, 1]
    float g;       // ∈ [0, 1]
    float b;       // ∈ [0, 1]
} RGB;

typedef struct {
public:
    float h;       // ∈ [0, 360]
    float s;       // ∈ [0, 1]
    float v;       // ∈ [0, 1]
} HSV;

class Colors
{
public:
  static RGB hsv2rgb(const HSV& hsv)
  {
      RGB rgb;
      float H = hsv.h, S = hsv.s, V = hsv.v,
              P, Q, T,
              fract;

      (H == 360.)?(H = 0.):(H /= 60.);
      fract = H - floor(H);

      P = V*(1. - S);
      Q = V*(1. - S*fract);
      T = V*(1. - S*(1. - fract));

      if (0. <= H && H < 1.)
          rgb = (RGB){.r = V, .g = T, .b = P};
      else if (1. <= H && H < 2.)
          rgb = (RGB){.r = Q, .g = V, .b = P};
      else if (2. <= H && H < 3.)
          rgb = (RGB){.r = P, .g = V, .b = T};
      else if (3. <= H && H < 4.)
          rgb = (RGB){.r = P, .g = Q, .b = V};
      else if (4. <= H && H < 5.)
          rgb = (RGB){.r = T, .g = P, .b = V};
      else if (5. <= H && H < 6.)
          rgb = (RGB){.r = V, .g = P, .b = Q};
      else
          rgb = (RGB){.r = 0., .g = 0., .b = 0.};

      return rgb;
  }
};

#endif
