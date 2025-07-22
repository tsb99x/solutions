package euler;

import java.io.IOException;
import java.util.stream.IntStream;

import common.Runner;

// https://projecteuler.net/problem=1
// https://oeis.org/A000027 -- The positive integers.

/*
 * <p>If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.</p>
 * <p>Find the sum of all the multiples of 3 or 5 below 1000.</p>
 */

public class P1SumOfMultiples {

    public static void main(String[] args) throws IOException {
//        System.out.println(procedural()); // 233168
//        Runner.timed(() -> procedural(1000), 10_000, 10);
//        System.out.println(functional()); // 233168
        Runner.timedI(i -> functional(1000), 1_000, 10);
//        System.out.println(solution(1000)); // 233168
        Runner.timedI(i -> solution(1000), 1_000, 10);
    }

    static int procedural(int number) {
        int sum = 0;
        for (int n = 0; n < number; n++)
            if (n % 3 == 0 || n % 5 == 0)
                sum += n;
        return sum;
    }

    static int functional(int number) {
        return IntStream.range(1, number)
                .parallel() // FIXME: Use when number is too big
                .filter(n -> n % 3 == 0 || n % 5 == 0)
                .sum();
    }

    public static int solution(int number) {
        int sum = 0;
        for (int i = 0; i < number / 15; i++)
            sum += 60 + i * 15 * 7;
        for (int i = number - (number % 15) + 1; i < number; i++)
            if (i % 3 == 0 || i % 5 == 0)
                sum += i;
        return sum;
    }

}
