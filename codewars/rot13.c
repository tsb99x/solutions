#include <ctype.h>

char *rot13 (char *str_out, const char *str_in)
{
  char base, *c = str_out;

  for (c = str_out; *str_in != '\0'; c++, str_in++) {
    base = (*str_in >= 'A' && *str_in <= 'Z') ? 'A' : (*str_in >= 'a' && *str_in <= 'z') ? 'a' : 0;
    *c = base ? (*str_in - base + 13) % 26 + base : *str_in;
  }
  *c = '\0';

  return str_out;
}
