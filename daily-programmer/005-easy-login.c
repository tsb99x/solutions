#include "utils.h"

#define LENGTH 256
#define OK NULL

typedef const char *error_t;

error_t authenticate(
    const char *ologin,
    const char *opassword
) {
    char ilogin[LENGTH], ipassword[LENGTH];
    int i;

    for (i = 0; i < 3; i++) {
        if (!input("login    > ", ilogin,    LENGTH)
        ||  !input("password > ", ipassword, LENGTH))
            return "failed to acquire input";

        if (!strcmp(ilogin, ologin)
        &&  !strcmp(ipassword, opassword))
            return OK;

        fputs("wrong login and password combination\n", stderr);
    }

    return "failed to authenticate several times";
}

int main(
    void
) {
    char ologin[LENGTH], opassword[LENGTH];
    FILE *passwords;
    error_t e = OK;

    passwords = fopen("./res/passwords.txt", "r");
    if (!passwords) {
        fputs("failed to open file ./res/passwords.txt\n", stderr);
        return -1;
    }

    if (2 != fscanf(passwords, "Credential {login = \"%255[^\"]\", password = \"%255[^\"]\"}\n", ologin, opassword)) {
        fputs("failed to read credentials data\n", stderr);
        return -1;
    }

    if ((e = authenticate(ologin, opassword))) {
        fprintf(stderr, "%s\n", e);
        return -1;
    }

    puts("authenticated successfully");

    if (fclose(passwords)) {
        fputs("failed to close passwords file\n", stderr);
        return -1;
    }

    return 0;
}
