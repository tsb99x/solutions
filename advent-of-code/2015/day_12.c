/*
        https://adventofcode.com/2015/day/12
        --- Day 12: JSAbacusFramework.io ---

        $ gcc -g -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_12{,.c} \
                && ./advent_of_code_2015_12
*/

#include <assert.h>
#include <stdio.h>
#include <string.h>

void mask(const char *src, const char *allowed, char *dst)
{
        for (; *src; src++, dst++)
                *dst = strchr(allowed, *src) ? *src : ' ';
        *dst = '\0';
}

int sum_of_all_numbers(const char *s)
{
        int a, chs, sum = 0;
        char *p, buf[255];

        mask(s, "0123456789-", buf);
        for (p = buf; sscanf(p, "%d %n", &a, &chs) == 1; p += chs)
                sum += a;
        return sum;
}

int main(void)
{
        assert(sum_of_all_numbers("[1,2,3]") == 6);
        assert(sum_of_all_numbers("{\"a\":2,\"b\":4}") == 6);
        assert(sum_of_all_numbers("[[[3]]]") == 3);
        assert(sum_of_all_numbers("{\"a\":{\"b\":4},\"c\":-1}") == 3);
        assert(sum_of_all_numbers("{\"a\":[-1,1]}") == 0);
        assert(sum_of_all_numbers("[-1,{\"a\":1}]") == 0);
        assert(sum_of_all_numbers("[]") == 0);
        assert(sum_of_all_numbers("{}") == 0);
        return 0;
}
