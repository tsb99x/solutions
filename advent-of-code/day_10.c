/*
        https://adventofcode.com/2015/day/10
        --- Day 10: Elves Look, Elves Say ---

        $ gcc -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_10{,.c} \
                && ./advent_of_code_2015_10
*/

#include <assert.h>
#include <stdio.h>
#include <string.h>

void look_and_say(const char *src, char dst[255])
{
        char buf[255] = {0};
        const char *c;

        *dst = '\0';
        for (; *src; src = c) {
                for (c = src; *c == *src; c++)
                        ;
                snprintf(buf, 255, "%ld%c", c - src, *src);
                strcat(dst, buf);
        }
}

int the_length_of_the_result(const char *src, int n)
{
        char buf_a[255], buf_b[255];

        strcpy(buf_a, src);
        while (n--) {
                look_and_say(buf_a, buf_b);
                strcpy(buf_a, buf_b);
        }
        return strlen(buf_a);
}

int main(void)
{
        assert(the_length_of_the_result("1", 5) == 6);
        return 0;
}
