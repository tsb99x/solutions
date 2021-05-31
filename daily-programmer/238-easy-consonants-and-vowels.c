/*

    https://www.reddit.com/r/dailyprogrammer/comments/3q9vpn/20151026_challenge_238_easy_consonants_and_vowels/

    [2015-10-26] Challenge #238 [Easy] Consonants and Vowels

    Description

    You were hired to create words for a new language.
    However, your boss wants these words to follow a strict pattern of consonants and vowels.
    You are bad at creating words by yourself, so you decide it would be best to randomly generate them.

    Your task is to create a program that generates a random word given a pattern of consonants (c) and vowels (v).

    Input Description

    Any string of the letters c and v, uppercase or lowercase.

    Output Description

    A random lowercase string of letters in which consonants (bcdfghjklmnpqrstvwxyz) occupy the given 'c' indices and vowels (aeiou) occupy the given 'v' indices.

    Sample Inputs

        cvcvcc

        CcvV

        cvcvcvcvcvcvcvcvcvcv

    Sample Outputs

        litunn

        ytie

        poxuyusovevivikutire

    Bonus

    - Error handling: make your program react when a user inputs a pattern that doesn't consist of only c's and v's.

    - When the user inputs a capital C or V, capitalize the letter in that index of the output.

    Credit

    This challenge was suggested by /u/boxofkangaroos.
    If you have any challenge ideas please share them on r/dailyprogrammer_ideas and there's a good chance we'll use them.

*/

#include "utils/panic.h"

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

const char consonants[] = "bcdfghjklmnpqrstvwxyz";
const char vowels[] = "aeiou";

const size_t consonants_size = sizeof consonants - 1;
const size_t vowels_size = sizeof vowels - 1;

char gen_char(const char ch)
{
    switch (ch) {
    case 'c':
        return consonants[rand() % consonants_size];
    case 'C':
        return (char)toupper(consonants[rand() % consonants_size]);
    case 'v':
        return vowels[rand() % vowels_size];
    case 'V':
        return (char)toupper(vowels[rand() % vowels_size]);
    default:
        panic_m("Unknown char '%c' found in input", ch);
    }
}

int main()
{
    srand((unsigned int)time(NULL));

    char input_buf[255];
    if (fgets(input_buf, sizeof input_buf, stdin) == NULL)
        panic_m("Failed to acquire input");

    for (char *ch = input_buf; *ch != '\n' && *ch != '\0'; ch++)
        printf("%c", gen_char(*ch));
}
