#include <inttypes.h>
#include <stdio.h>

uint32_t IP_to_num (const char *IP)
{
  int a, b, c, d;
  sscanf(IP, "%d.%d.%d.%d", &a, &b, &c, &d);
	return a << 24 | b << 16 | c << 8 | d;
}

char *num_to_IP (uint32_t num, char *IP)
{
	*IP = '\0';
  sprintf(IP, "%d.%d.%d.%d", num >> 24, num >> 16 & 0xFF, num >> 8 & 0xFF, num & 0xFF);
	return IP;
}
