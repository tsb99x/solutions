#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define SET_SIZE 4096

struct point_2d {
        int x;
        int y;
};

static void move(struct point_2d *pos, char ch)
{
        switch (ch) {
        case '>':
                pos->x++;
                return;
        case '<':
                pos->x--;
                return;
        case '^':
                pos->y++;
                return;
        case 'v':
                pos->y--;
                return;
        default:
                return;
        }
}

static int set_add(int set_count, struct point_2d set[], struct point_2d elem)
{
        for (int i = 0; i < set_count; i++) {
                if ((set[i].x == elem.x) && (set[i].y == elem.y)) {
                        return set_count;
                }
        }

        if (set_count >= SET_SIZE) {
                (void)fputs("Failed to set_add, capacity reached\n", stderr);
                exit(EXIT_FAILURE);
        }

        set[set_count] = elem;
        return set_count + 1;
}

static int how_many_houses(FILE *stream)
{
        int ch = 0;
        struct point_2d pos = {0};
        struct point_2d set[SET_SIZE] = {0};
        int set_count = set_add(0, set, pos);

        while ((ch = fgetc(stream)) != EOF) {
                move(&pos, (char)ch);
                set_count = set_add(set_count, set, pos);
        }

        if (!feof(stream)) {
                perror("Failed to fgetc");
                exit(EXIT_FAILURE);
        }

        return set_count;
}

int main(int argc, char *argv[])
{
        int houses = 0;

        if (argc > 1) {
                (void)fputs("No arguments are accepted!\n"
                            "Use like this: part_i < input\n",
                            stderr);
                return EXIT_FAILURE;
        }
        (void)argv;

        houses = how_many_houses(stdin);
        (void)printf("%d\n", houses);

        return EXIT_SUCCESS;
}
