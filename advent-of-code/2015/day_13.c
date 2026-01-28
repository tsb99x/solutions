/*
        https://adventofcode.com/2015/day/13
        --- Day 13: Knights of the Dinner Table ---

        $ gcc -g -O2 -std=c99 \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_13{,.c} \
                && ./advent_of_code_2015_13
*/

#include <assert.h>
#include <stdio.h>
#include <string.h>

int total_change_in_happiness(const char *s)
{
        char pers_a[255], pers_b[255], action[255];
        int value, chs = 0;

        printf("application running...\n");

        while (
            sscanf(s,
                   "%s would %s %d happiness units by sitting next to %s %n",
                   pers_a, action, &value, pers_b, &chs) == 4) {
                printf("chars: %d\n", chs);
                value *= !strcmp("gain", action) ? 1 : -1;
                printf("%s <- %s (%d) -> %s\n", pers_a, action, value, pers_b);
                s += chs;
        }
        return 0;
}

int main(void)
{
        const char *happiness =
            "Alice would gain 54 happiness units by sitting next to Bob.\n"
            "Alice would lose 79 happiness units by sitting next to Carol.\n"
            "Alice would lose 2 happiness units by sitting next to David.\n"
            "Bob would gain 83 happiness units by sitting next to Alice.\n"
            "Bob would lose 7 happiness units by sitting next to Carol.\n"
            "Bob would lose 63 happiness units by sitting next to David.\n"
            "Carol would lose 62 happiness units by sitting next to Alice.\n"
            "Carol would gain 60 happiness units by sitting next to Bob.\n"
            "Carol would gain 55 happiness units by sitting next to David.\n"
            "David would gain 46 happiness units by sitting next to Alice.\n"
            "David would lose 7 happiness units by sitting next to Bob.\n"
            "David would gain 41 happiness units by sitting next to Carol.\n";
        assert(total_change_in_happiness(happiness) == 330);
        return 0;
}
