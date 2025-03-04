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

static int basement_enter(FILE *stream)
{
        int floor = 0;
        int position = 1;
        int ch = 0;

        while ((ch = fgetc(stream)) != EOF) {
                floor += char_to_movement((char)ch);
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

int main(int argc, char *argv[])
{
        int position = 0;

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
