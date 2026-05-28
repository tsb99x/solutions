#include <stdio.h>

int main(void)
{
        unsigned int hash = 0;
        int c;

        while ((c = getchar()) != EOF) {
                if (c == '\n') {
                        printf("%u\n", hash);
                        hash = 0;
                } else {
                        hash += c;
                }
        }

        if (hash != 0) {
                printf("%u\n", hash);
        }

        return 0;
}
