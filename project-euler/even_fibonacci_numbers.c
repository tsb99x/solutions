// 1, 2, 3, 5, 8, 13, 21, 34, 55, 89

// values do not exceed four million

// 1, (2), 3, 5, (8), 13, 21, (34), 55, 89, (144)

// 3 * n + 2 * p

// (1 + 2) + (2 + (1 + 2))
// (8 + 5) + (8 + (8 + 5))

#include <assert.h>
#include <stdio.h>

long even_fibonacci_sum(int limit)
{
        long sum = 2;

        long p = 1;
        long c = 2;
        long n = 3;
        while (n < limit) {
                if (n % 2 == 0)
                        sum += n;
                n = c + p;
                p = c;
                c = n;
        }

        return sum;
}

#include <limits.h>

int main(void)
{
        assert(even_fibonacci_sum(50) == 44);
        assert(even_fibonacci_sum(500) == 188);
        assert(even_fibonacci_sum(1000) == 798);
        assert(even_fibonacci_sum(4000000) == 4613732);
        printf("%ld\n", even_fibonacci_sum(INT_MAX));

        return 0;
}
