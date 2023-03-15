package euler;

import java.util.stream.IntStream;

import common.Runner;

// https://projecteuler.net/problem=3
// https://oeis.org/A000040 -- The prime numbers.

/*
 * <p>The prime factors of 13195 are 5, 7, 13 and 29.</p>
 * <p>What is the largest prime factor of the number 600851475143 ?</p>
 */

public class P3LargestPrimeFactor {

    public static void main(String[] args) {
        /*
         * var number = 600851475143L; for (int n = 2; n < Math.sqrt(number);
         * n++) if (number % n == 0) number /= n--; System.out.println(number);
         * // 6857
         */
        Runner.timed(P3LargestPrimeFactor::procedural, 10_000, 10);
    }

    static int procedural() {
        var number = 600851475143L;
        for (int n = 2; n <= Math.sqrt(number); n++) {
            if (number % n == 0) {
                number /= n--;
            }
        }
        int[] arr = { 1, 2, 3 };
        IntStream.of(arr).summaryStatistics().getAverage();
        return (int) number;
    }

}
