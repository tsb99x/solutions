#include <stdio.h>
#include <stdlib.h>

static int min(int a, int b) { return (a < b) ? a : b; }

static int feet_of_ribbon(int l, int w, int h)
{
        int a = l + w;
        int b = w + h;
        int c = h + l;
        int wrap = 2 * min(min(a, b), c);
        int bow = l * w * h;

        return wrap + bow;
}

static int total_feet_of_ribbon(FILE *stream)
{
        int l = 0;
        int w = 0;
        int h = 0;
        int total = 0;

        while (fscanf(stream, "%3d x %3d x %3d", &l, &w, &h) == 3) {
                if ((l <= 0) || (w <= 0) || (h <= 0)) {
                        (void)fprintf(stderr,
                                      "All sizes must be >= 0, "
                                      "but dimensions were: %d x %d x %d\n",
                                      l, w, h);
                        exit(EXIT_FAILURE);
                }
                total += feet_of_ribbon(l, w, h);
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
                (void)fprintf(stderr,
                              "No arguments are accepted!\n"
                              "Use IO redirection: %s < input\n",
                              argv[0]);
                return EXIT_FAILURE;
        }

        total = total_feet_of_ribbon(stdin);
        (void)printf("%d\n", total);

        return EXIT_SUCCESS;
}
