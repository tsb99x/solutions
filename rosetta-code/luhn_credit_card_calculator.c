/*
        https://rosettacode.org/wiki/Luhn_test_of_credit_card_numbers
        https://en.wikipedia.org/wiki/Luhn_algorithm
*/

#include <assert.h>
#include <ctype.h>
#include <string.h>

#define C_LUHN_S_MAX_LEN_W_NULL 17
#define ERR_LUHN_S_TOO_LONG -1
#define ERR_LUHN_S_EMPTY -2
#define ERR_LUHN_S_CONTAINS_NON_DIGITS -3

int luhn(const char *s)
{
        /* (N * 2) / 10 + (N * 2) % 10 for each N in [ 0 ... 9 ] */
        static const int lut[] = {0, 2, 4, 6, 8, 1, 3, 5, 7, 9};

        char *c;
        int sum, is_even, digit;

        c = memchr(s, '\0', C_LUHN_S_MAX_LEN_W_NULL);
        if (!c)
                return ERR_LUHN_S_TOO_LONG;
        if (c - s <= 0)
                return ERR_LUHN_S_EMPTY;

        sum = 0;
        is_even = 0;
        while (c-- > s) {
                if (!isdigit(*c))
                        return ERR_LUHN_S_CONTAINS_NON_DIGITS;
                digit = *c - '0';
                sum += is_even ? lut[digit] : digit;
                is_even = !is_even;
        }

        return sum % 10 == 0;
}

int main(void)
{
        assert(luhn("") == ERR_LUHN_S_EMPTY);
        assert(luhn("12341234123412341") == ERR_LUHN_S_TOO_LONG);
        assert(luhn("123412341234A") == ERR_LUHN_S_CONTAINS_NON_DIGITS);

        assert(luhn("49927398716") == 1);
        assert(luhn("49927398717") == 0);
        assert(luhn("1234567812345678") == 0);
        assert(luhn("1234567812345670") == 1);

        return 0;
}
