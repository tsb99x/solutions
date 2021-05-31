#include "utils.h"

#define LENGTH 256

char get_base(
    char c
) {
    if (c >= 'a' && c <= 'z')
        return 'a';
    if (c >= 'A' && c <= 'Z')
        return 'A';
    return c;
}

char rotc(
    char c,
    int shift
) {
    char base = get_base(c);

    if (base == c)
        return c;
    return base + (c - base + shift) % 26;
}

void rots(
    char *s,
    int shift
) {
    while (*s)
        *s++ = rotc(*s, shift);
}

int main(
    void
) {
    char in[LENGTH];

    if (!input("string to encode > ", in, sizeof(in))) {
        fputs("failed to acquire input\n", stderr);
        return -1;
    }

    rots(in, 13);
    printf("rot13 is '%s'\n", in);
    rots(in, 13);
    printf("rot13 is '%s'\n", in);
    return 0;
}
