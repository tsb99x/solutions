#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUF_SIZE 32

static int count_vowels(size_t len, const char sz[])
{
        int vowels = 0;

        for (size_t i = 0; i < len; i++) {
                if (strchr("aeiou", sz[i]) != NULL) {
                        vowels++;
                }
        }

        return vowels;
}

static bool has_double_letter(size_t len, const char sz[])
{
        for (size_t i = 1; i < len; i++) {
                if (sz[i] == sz[i - 1U]) {
                        return true;
                }
        }

        return false;
}

static bool has_disallowed_substrings(const char *sz)
{
        return strstr(sz, "ab") || strstr(sz, "cd") || strstr(sz, "pq")
               || strstr(sz, "xy");
}

static bool is_string_nice(const char *sz)
{
        size_t len = strlen(sz);
        return (count_vowels(len, sz) >= 3) && has_double_letter(len, sz)
               && !has_disallowed_substrings(sz);
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
