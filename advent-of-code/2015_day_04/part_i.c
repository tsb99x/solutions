#include <limits.h>
#include <openssl/err.h>
#include <openssl/evp.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_ARG_LEN 32
#define BUF_SIZE 64

static long find_five_zeroes(const char prefix[])
{
        char buf[BUF_SIZE] = {0};
        unsigned char md[EVP_MAX_MD_SIZE] = {0};
        unsigned int md_size = 0;

        for (long i = 1; i < LONG_MAX; i++) {
                if (snprintf(buf, sizeof(buf), "%s%ld", prefix, i) < 0) {
                        perror("Failed to snprintf");
                        exit(EXIT_FAILURE);
                }

                if (!EVP_Digest(
                        buf, strlen(buf), md, &md_size, EVP_md5(), NULL)) {
                        ERR_print_errors_fp(stderr);
                        exit(EXIT_FAILURE);
                }

                // NOLINTNEXTLINE
                if ((md[0] == 0x00) && (md[1] == 0x00) && (md[2] < 0x10)) {
                        return i;
                }
        }

        return -1;
}

int main(int argc, char *argv[])
{
        size_t arg_len = 0;
        long suffix = 0;

        if (argc != 2) {
                (void)fputs("Only one argument is accepted!\n"
                            "Use like this: part_i prefix\n",
                            stderr);
                return EXIT_FAILURE;
        }
        (void)argv;

        arg_len = strlen(argv[1]);
        if (arg_len > MAX_ARG_LEN) {
                (void)fprintf(stderr,
                              "Secret prefix argument must be less than %d "
                              "chars of length, but was %zu\n",
                              MAX_ARG_LEN,
                              arg_len);
                return EXIT_FAILURE;
        }

        suffix = find_five_zeroes(argv[1]);
        if (suffix < 0) {
                (void)fprintf(stderr,
                              "Failed to find MD5 with 5 zeroes for %s\n",
                              argv[1]);
                return EXIT_FAILURE;
        }
        (void)printf("%ld\n", suffix);

        return EXIT_SUCCESS;
}
