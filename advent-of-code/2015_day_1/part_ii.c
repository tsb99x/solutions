/*

--- Part Two ---
https://adventofcode.com/2015/day/1

$ gcc -O2 -ansi \
        -Wall -Wextra -Wpedantic -Werror \
        -fsanitize=undefined,address \
        -o /tmp/part_ii \
        part_ii.c

$ clang -O2 -ansi \
        -Weverything -Werror \
        -fsanitize=undefined,address \
        -o /tmp/part_ii \
        part_ii.c

$ tcc -DTEST -b -run part_ii.c

*/

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

static int basement_enter(FILE *stream)
{
        /* int ∈ [-32768..32767] */
        int floor = 0;
        int position = 1;
        int ch = 0;

        while ((ch = fgetc(stream)) != EOF) {
                switch (ch) {
                case '(':
                        floor += 1;
                        break;
                case ')':
                        floor -= 1;
                        break;
                default:
                        break;
                }

                if (floor < 0) {
                        return position;
                }

                position++;
        }

        if (!feof(stream)) {
                perror("Failed to fgetc");
                exit(EXIT_FAILURE);
        }

        return -1;
}

#ifndef TEST

int main(int argc, char *argv[])
{
        int position;

        (void)argc;
        (void)argv;

        position = basement_enter(stdin);
        if (position < 0) {
                (void)puts("Santa has not visited the basement");
                return EXIT_FAILURE;
        }
        (void)printf("%d\n", position);

        return EXIT_SUCCESS;
}

#else

static FILE *tmpfile_with(const char *sz)
{
        FILE *file = tmpfile();

        if (file == NULL) {
                perror("Failed to tmpfile");
                exit(EXIT_FAILURE);
        }
        if (fputs(sz, file) < 0) {
                perror("Failed to fputs");
                exit(EXIT_FAILURE);
        }
        rewind(file);
        return file;
}

int main(int argc, char *argv[])
{
        (void)argc;
        (void)argv;

        assert(basement_enter(tmpfile_with(")")) == 1);
        assert(basement_enter(tmpfile_with("()())")) == 5);
        (void)puts("All tests were successful!");

        return EXIT_SUCCESS;
}

#endif
