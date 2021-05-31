#pragma once

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <string.h>

bool input(
    const char *prompt,
    char *dest,
    size_t dest_size
) {
    size_t len;

    fputs(prompt, stdout);
    dest = fgets(dest, dest_size, stdin);
    if (!dest)
        return false;
    len = strlen(dest);
    if (len-- && dest[len] == '\n')
        dest[len] = '\0';
    return true;
}
