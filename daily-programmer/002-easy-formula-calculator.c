#include "utils.h"

#define LENGTH 256

char read_mode(
    const char *in
) {
    if (!strcmp(in, "f"))
        return 'f';
    if (!strcmp(in, "m"))
        return 'm';
    if (!strcmp(in, "a"))
        return 'a';
    return 0;
}

bool read_param(
    char param,
    float *res
) {
    printf("input value of [%c] > ", param);
    if (scanf("%f", res) != 1) {
        fputs("failed to parse float input\n", stderr);
        return false;
    }
    return true;
}

float calc(
    char mode,
    float f,
    float m,
    float a
) {
    if (mode == 'f')
        return m * a;
    if (mode == 'm')
        return f / a;
    if (mode == 'a')
        return f / m;
    return 0;
}

int main(
    void
) {
    char mode, in[LENGTH];
    float f, m, a;

    if (!input("choose input mode [f] = [m] * [a] > ", in, LENGTH)) {
        fputs("failed to acquire input\n", stderr);
        return -1;
    }

    mode = read_mode(in);
    if (!mode) {
        fputs("only [f], [m], or [a] modes are allowed\n", stderr);
        return -1;
    }

    if (mode == 'm' || mode == 'a')
        if (!read_param('f', &f))
            return -1;
    if (mode == 'f' || mode == 'a')
        if (!read_param('m', &m))
            return -1;
    if (mode == 'f' || mode == 'm')
        if (!read_param('a', &a))
            return -1;

    printf("[%c] = %f\n", mode, calc(mode, f, m, a));

    return 0;
}
