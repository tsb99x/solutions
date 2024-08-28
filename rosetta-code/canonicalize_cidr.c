/*
        https://rosettacode.org/wiki/Canonicalize_CIDR
*/

#include <assert.h>
#include <stdio.h>
#include <string.h>

#include <stdint.h>

#define C_CANONICALIZE_IN_MAX_LEN_W_NULL 19
#define ERR_CANONICALIZE_IN_TOO_LONG -1
#define ERR_CANONICALIZE_IN_BAD_FORMAT -2

int canonicalize(const char *in, char *out)
{
        const char *in_eos;
        int matched, a, b, c, d, m, chars;
        uint32_t addr;

        *out = '\0';

        in_eos = memchr(in, '\0', C_CANONICALIZE_IN_MAX_LEN_W_NULL);
        if (!in_eos)
                return ERR_CANONICALIZE_IN_TOO_LONG;

        matched = sscanf(in, "%d.%d.%d.%d/%d %n", &a, &b, &c, &d, &m, &chars);
        if (matched != 5 || chars != (in_eos - in)
                || a < 0 || a > 255
                || b < 0 || b > 255
                || c < 0 || c > 255
                || d < 0 || d > 255
                || m < 0 || m > 32)
                return ERR_CANONICALIZE_IN_BAD_FORMAT;

        addr = a << 24 | b << 16 | c << 8 | d;
        addr &= ~(~(uint32_t) 0 >> m);

        sprintf(out, "%d.%d.%d.%d/%d", addr >> 24, addr >> 16 & 0xFF,
                addr >> 8 & 0xFF, addr & 0xFF, m);

        return 0;
}

int main(void)
{
        int err;
        char buf[19];

        err = canonicalize("", buf);
        assert(err == ERR_CANONICALIZE_IN_BAD_FORMAT);
        err = canonicalize("87.70.141/22", buf);
        assert(err == ERR_CANONICALIZE_IN_BAD_FORMAT);
        err = canonicalize("87.70.141.1/22E", buf);
        assert(err == ERR_CANONICALIZE_IN_BAD_FORMAT);
        err = canonicalize("255.255.255.255/255", buf);
        assert(err == ERR_CANONICALIZE_IN_TOO_LONG);

        err = canonicalize("87.70.141.1/22", buf);
        assert(err == 0);
        assert(strcmp(buf, "87.70.140.0/22") == 0);
        err = canonicalize("36.18.154.103/12", buf);
        assert(err == 0);
        assert(strcmp(buf, "36.16.0.0/12") == 0);
        err = canonicalize("62.62.197.11/29", buf);
        assert(err == 0);
        assert(strcmp(buf, "62.62.197.8/29") == 0);
        err = canonicalize("67.137.119.181/4", buf);
        assert(err == 0);
        assert(strcmp(buf, "64.0.0.0/4") == 0);
        err = canonicalize("161.214.74.21/24", buf);
        assert(err == 0);
        assert(strcmp(buf, "161.214.74.0/24") == 0);
        err = canonicalize("184.232.176.184/18", buf);
        assert(err == 0);
        assert(strcmp(buf, "184.232.128.0/18") == 0);

        err = canonicalize("0.0.0.0/0", buf);
        assert(err == 0);
        assert(strcmp(buf, "0.0.0.0/0") == 0);
        err = canonicalize("184.232.176.184/0", buf);
        assert(err == 0);
        assert(strcmp(buf, "0.0.0.0/0") == 0);

        return 0;
}
