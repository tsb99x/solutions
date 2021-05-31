#include "utils.h"

#include <stdlib.h>
#include <time.h>

#define OK NULL;

typedef const char *error_t;

const char alpha[] =
    "abcdefghijklmnopqrstuvwxyz"
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    "1234567890"
    ".,-=+@$^&:;";
const size_t alpha_len = sizeof(alpha) - 1;

bool convert_s_to_i(
    const char *s,
    int *i
) {
    char *p;

    *i = strtol(s, &p, 10);
    return p == s + strlen(s);
}

error_t read_int(
    const char *prompt,
    int *res
) {
    char in[1024];

    if (!input(prompt, in, sizeof(in)))
        return "failed to acquire input";
    if (!convert_s_to_i(in, res))
        return "failed to convert string to integer";
    return OK;
}

error_t handle_error(
    error_t e
) {
    if (e)
        fprintf(stderr, "%s\n", e);
    return e;
}

int main(
    void
) {
    int i, j, len, cnt;
    error_t e;

    srand(time(NULL));

    e = read_int("password length > ", &len);
    if (handle_error(e))
        return -1;
    e = read_int("passwords count > ", &cnt);
    if (handle_error(e))
        return -1;

    for (i = 0; i < cnt; i++, putchar('\n'))
        for (j = 0; j < len; j++)
            putchar(alpha[rand() % alpha_len]);

    return 0;
}
