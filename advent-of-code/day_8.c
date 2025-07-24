/*
        https://adventofcode.com/2015/day/8
        --- Day 8: Matchsticks ---

        $ gcc -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_8{,.c} \
                && ./advent_of_code_2015_8
*/

#include <assert.h>
#include <string.h>

int number_of_chars_delta(const char *s)
{
        int mem = strlen(s);
        const char *p = s;
        while ((p = strstr(p, "\\\""))) {
                mem--;
                p++;
        }
        p = s;
        while ((p = strstr(p, "\\x"))) {
                mem -= 3;
                p++;
        }
        mem -= 2;
        return strlen(s) - mem;
}

int main(void)
{
        assert(number_of_chars_delta("\"\"") == 2);
        assert(number_of_chars_delta("\"abc\"") == 2);
        assert(number_of_chars_delta("\"aaa\\\"aaa\"") == 3);
        assert(number_of_chars_delta("\"\\x27\"") == 5);
        return 0;
}
