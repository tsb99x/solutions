#include <stdbool.h>
#include <ctype.h>

bool alphanumeric(const char* strin) {
  char *c;

  if (*strin == '\0')
    return false;

  c = strin;
  while (*c != '\0') {
    if (!isalnum(*c))
      return false;
    c++;
  }

  return true;
}
