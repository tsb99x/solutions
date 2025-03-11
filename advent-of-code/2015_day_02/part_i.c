#include <stdio.h>
#include <stdlib.h>

static int min(int a, int b) { return (a < b) ? a : b; }

static int feet_of_paper(int l, int w, int h)
{
        int a = l * w;
        int b = w * h;
        int c = h * l;
        int wrap = (2 * a) + (2 * b) + (2 * c);
        int slack = min(min(a, b), c);

        return wrap + slack;
}

static int total_feet_of_paper(FILE *stream)
{
        int l = 0;
        int w = 0;
        int h = 0;
        int total = 0;

        while (fscanf(stream, "%3dx%3dx%3d", &l, &w, &h) == 3) {
                total += feet_of_paper(l, w, h);
        }

        if (!feof(stream)) {
                perror("Failed to fscanf");
                exit(EXIT_FAILURE);
        }

        return total;
}

int main(int argc, char *argv[])
{
        int total = 0;

        if (argc > 1) {
                (void)fputs("No arguments are accepted!\n"
                            "Use like this: part_i < input\n",
                            stderr);
                return EXIT_FAILURE;
        }
        (void)argv;

        total = total_feet_of_paper(stdin);
        (void)printf("%d\n", total);

        return EXIT_SUCCESS;
}
