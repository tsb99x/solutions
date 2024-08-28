/*
        https://rosettacode.org/wiki/Run-length_encoding
*/

#include <assert.h>
#include <stdio.h>
#include <string.h>

#define ERR_RLE_OUT_BUFFER_EXHAUSTED -1

int rle(const char *in, char *out, size_t out_size)
{
        const char *current, *p;
        char *out_eos;
        int n, printed;

        *out = '\0';

        current = in;
        out_eos = out + out_size;
        while (*current != '\0' && out < out_eos) {
                for (p = current, n = 0; *p == *current; n++, p++)
                        ;
                printed = snprintf(out, out_eos - out, "%d%c", n, *current);
                out += printed;
                current = p;
        }

        if (out >= out_eos)
                return ERR_RLE_OUT_BUFFER_EXHAUSTED;

        return 0;
}

int main(void)
{
        int err;
        char buf[19];

        err = rle("WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWW"
                  "WWWWWWW",
                  buf, 19);
        assert(err == 0);
        assert(strcmp(buf, "12W1B12W3B24W1B14W") == 0);

        err = rle("1211", buf, 19);
        assert(err == 0);
        assert(strcmp(buf, "111221") == 0);

        return 0;
}
