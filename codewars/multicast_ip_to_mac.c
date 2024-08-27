#include <stdio.h>

/* Size of macStr, 18 bytes, is guaranteed */
void mcastIp2Mac(const char* ip, char* macStr) {
  int a, b, c, d;

  sscanf(ip, "%d.%d.%d.%d", &a, &b, &c, &d);
  sprintf(macStr, "01:00:5E:%02X:%02X:%02X", b & 0b01111111, c, d);
}
