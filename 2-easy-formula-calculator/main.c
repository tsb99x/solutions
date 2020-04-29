#include <stdio.h>
#include <string.h>

#define LENGTH 255

int main(
        void
) {
        char mode, input[LENGTH];
        float f, m, a;

        printf("choose input mode [f] = [m] * [a] > ");
        gets(input);

        if (!strcmp(input, "f")) {
                mode = 'f';
        } else if (!strcmp(input, "m")) {
                mode = 'm';
        } else if (!strcmp(input, "a")) {
                mode = 'a';
        } else {
                fprintf(stderr, "only [f], [m], or [a] modes are allowed");
                return -1;
        }

        if (mode == 'm' || mode == 'a') {
                printf("input value of [f] > ");
                scanf("%f", &f);
        }
        if (mode == 'f' || mode == 'a') {
                printf("input value of [m] > ");
                scanf("%f", &m);
        }
        if (mode == 'f' || mode == 'm') {
                printf("input value of [a] > ");
                scanf("%f", &a);
        }

        if (mode == 'f') {
                f = m * a;
                printf("[f] = %f\n", f);
        } else if (mode == 'm') {
                m = f / a;
                printf("[m] = %f\n", m);
        } else if (mode == 'a') {
                a = f / m;
                printf("[a] = %f\n", a);
        }

        return 0;
}
