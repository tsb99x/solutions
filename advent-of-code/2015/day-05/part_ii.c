#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUF_SIZE 32

static bool has_pair_of_non_overlaping_duplicates(size_t len, const char sz[])
{
        char buf[3] = {0};

        for (size_t i = 1; i < len; i++) {
                buf[0] = sz[i - 1U];
                buf[1] = sz[i];

                if (strstr(&sz[i + 1U], buf) != NULL) {
                        return true;
                }
        }

        return false;
}

static bool has_same_letter_with_spacing(size_t len, const char sz[])
{
        for (size_t i = 2; i < len; i++) {
                if (sz[i] == sz[i - 2U]) {
                        return true;
                }
        }

        return false;
}

static bool is_string_nice(const char *sz)
{
        size_t len = strlen(sz);
        return has_pair_of_non_overlaping_duplicates(len, sz)
               && has_same_letter_with_spacing(len, sz);
}

static int count_nice_strings(FILE *stream)
{
        char buf[BUF_SIZE] = {0};
        int counter = 0;

        while (fgets(buf, sizeof(buf), stream) != NULL) {
                size_t len = strcspn(buf, "\n");
                if (len >= sizeof(buf) - 1U) {
                        (void)fputs("Failed to fgets: Line is too long",
                                    stderr);
                        exit(EXIT_FAILURE);
                }
                buf[len] = '\0';

                if (is_string_nice(buf)) {
                        counter++;
                }
        }

        if (!feof(stream)) {
                perror("Failed to fgets");
                exit(EXIT_FAILURE);
        }

        return counter;
}

int main(int argc, char *argv[])
{
        int nice_count = 0;

        if (argc > 1) {
                (void)fprintf(stderr,
                              "No arguments are accepted!\n"
                              "Use IO redirection: %s < input\n",
                              argv[0]);
                return EXIT_FAILURE;
        }

        nice_count = count_nice_strings(stdin);
        (void)printf("%d\n", nice_count);

        return EXIT_SUCCESS;
}
