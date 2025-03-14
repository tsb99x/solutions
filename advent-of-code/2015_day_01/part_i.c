#include <stdio.h>
#include <stdlib.h>

static int char_to_movement(char ch)
{
        switch (ch) {
        case '(':
                return 1;
        case ')':
                return -1;
        default:
                return 0;
        }
}

static int what_floor(FILE *stream)
{
        int floor = 0;
        int ch = 0;

        while ((ch = fgetc(stream)) != EOF) {
                floor += char_to_movement((char)ch);
        }

        if (!feof(stream)) {
                perror("Failed to fgetc");
                exit(EXIT_FAILURE);
        }

        return floor;
}

int main(int argc, char *argv[])
{
        int floor = 0;

        if (argc > 1) {
                (void)fputs("No arguments are accepted!\n"
                            "Use like this: part_i < input\n",
                            stderr);
                return EXIT_FAILURE;
        }
        (void)argv;

        floor = what_floor(stdin);
        (void)printf("%d\n", floor);

        return EXIT_SUCCESS;
}
