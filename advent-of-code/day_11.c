/*
        https://adventofcode.com/2015/day/11
        --- Day 11: Corporate Policy ---

        $ gcc -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_11{,.c} \
                && ./advent_of_code_2015_11
*/

#include <assert.h>
#include <string.h>

long base_26_to_base_10(const char *s)
{
        long x = 0;

        for (; *s; s++)
                x = x * 26 + *s - 'a' + 1;
        /* overflow? */
        return x;
}

void base_10_to_base_26(long x, char dst[255])
{
        char buf[255];
        char *c, *d;

        for (c = buf; x; x /= 26, c++)
                *c = x % 26 + 'a' - 1;
        *c = '\0';

        d = dst;
        while (c-- > buf)
                *d++ = *c;
        *d = '\0';
}

int has_ascends(const char *s)
{
        static const char *ascends[] = {"abc", "bcd", "cde", "def", "efg",
                                        "fgh", "pqr", "qrs", "rst", "stu",
                                        "tuv", "uvw", "vwx", "wxy", "xyz"};
        int i;
        for (i = 0; i < 15; i++)
                if (strstr(s, ascends[i]))
                        return 1;
        return 0;
}

int count_doubles(const char *s)
{
        static const char *doubles[] = {"aa", "bb", "cc", "dd", "ee", "ff",
                                        "gg", "hh", "jj", "kk", "mm", "nn",
                                        "pp", "qq", "rr", "ss", "tt", "uu",
                                        "vv", "ww", "xx", "yy", "zz"};
        int i, cnt = 0;
        const char *p;
        for (i = 0; i < 23; i++) {
                p = s;
                while ((p = strstr(p, doubles[i]))) {
                        cnt++;
                        p += 2;
                }
        }
        return cnt;
}

void next_password(const char *pass, char buf[255])
{

        long x = base_26_to_base_10(pass);
        for (;;) {
                x++;
                base_10_to_base_26(x, buf);
                if (!strpbrk(buf, "iol") && has_ascends(buf) &&
                    count_doubles(buf) >= 2)
                        return;
        }
}

int main(void)
{
        char buf[255];
        next_password("abcdefgh", buf);
        assert(!strcmp(buf, "abcdffaa"));
        next_password("ghijklmn", buf);
        assert(!strcmp(buf, "ghjaabcc"));
        return 0;
}
