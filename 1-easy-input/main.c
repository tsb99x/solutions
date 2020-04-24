#include <stdio.h>
#include <string.h>

#define LENGTH 255

int main(
        void
) {
        const char format[] = "your name is %s, you are %s years old, and your username is %s\n";
        char name[LENGTH], age[LENGTH], username[LENGTH];
        FILE *log;

        printf("input your name > ");
        gets(name);
        printf("input your age > ");
        gets(age);
        printf("input your username > ");
        gets(username);

        printf(format, name, age, username);

        log = fopen("log.txt", "a");
        if (!log) {
                fprintf(stderr, "could not open file log.txt\n");
                return -1;
        }
        fprintf(log, format, name, age, username);
        fclose(log);
}
