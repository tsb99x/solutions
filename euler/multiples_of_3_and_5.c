#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include <limits.h>

#if LONG_MAX == INT_MAX
#error "LONG_MAX must be bigger than INT_MAX"
#endif

#if LONG_LONG_MAX == LONG_MAX
#error LONGLONG
#endif

//https://projecteuler.net/problem=1

// long find_multiples(int limit)
// {
//         int i;
//         long sum = 0;

//         for (i = 1; i < limit; i++)
//                 if (i % 3 == 0 || i % 5 == 0) {
//                         sum += i;
//                         // if (sum < 0) {
//                         //         printf("Overflow has been detected!\n");
//                         //         abort();
//                         // }
//                 }

//         return sum;
// }

long find_multiples(int limit)
{
        int i;
        long sum = 0;

        for (i = 3; i < limit; i += 3)
                sum += i;

        for (i = 5; i < limit; i += 5)
                if (i % 3 != 0)
                        sum += i;

        return sum;
}

int main(void)
{
        printf("%ld\n", LONG_MAX);
        assert(find_multiples(10) == 23);
        assert(find_multiples(1000) == 233168);
        assert(find_multiples(1000000000) == 233333333166666668);
        return 0;
}
