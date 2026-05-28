#include <stdio.h>

int main(void)
{
        unsigned long hash = 0;
        int c;

        while ((c = getchar()) != EOF) {
                if (c == '\n') {
                        printf("%lu\n", hash);
                        hash = 0;
                } else {
                        hash = c + (hash << 6) + (hash << 16) - hash;
                }
        }

        if (hash != 0) {
                printf("%lu\n", hash);
        }

        return 0;
}
