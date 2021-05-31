#include "utils.h"

#define LENGTH 256

const char format[] = "your name is %s, you are %s years old, and your username is %s\n";

int main(
    void
) {
    char name[LENGTH], age[LENGTH], username[LENGTH];
    FILE *log;

    if (!input("your name > ",     name,     LENGTH)
    ||  !input("your age > ",      age,      LENGTH)
    ||  !input("your username > ", username, LENGTH)) {
        fputs("failed to acquire input\n", stderr);
        return -1;
    }

    printf(format, name, age, username);

    log = fopen("tmp/log.txt", "a");
    if (!log) {
        fputs("failed to open log file\n", stderr);
        return -1;
    }

    fprintf(log, format, name, age, username);

    if (fclose(log)) {
        fputs("failed to close log file\n", stderr);
        return -1;
    }

    return 0;
}
