package euler;

import java.util.ArrayList;
import java.util.Arrays;

import common.Runner;

// https://projecteuler.net/problem=7
// https://oeis.org/A000040 -- The prime numbers.

// <p>By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.</p>
// <p>What is the 10 001st prime number?</p>

public class P710001stPrime {

    public static void main(String[] args) {
        System.out.println(solution2(10_001)); // 104743
        Runner.timed(() -> solution2(10_001), 10, 3);
        System.out.println(solution(10_001)); // 104743
        Runner.timed(() -> solution(10_001), 10, 3);
        System.out.println(alternative(10_001)); // 104743
        Runner.timed(() -> alternative(10_001), 10, 3);
    }

    static int solution(int n) {
        var primes = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7));
        positive_numbers: for (int i = 7; primes.size() < n; i += 2) {
            for (int prime : primes)
                if (i % prime == 0)
                    continue positive_numbers;
            primes.add(i);
        }
        return primes.get(n - 1);
    }

    static int solution2(int n) {
        int[] primes = new int[n];
        primes[0] = 2;
        primes[1] = 3;
        primes[2] = 5;
        primes[3] = 7;
        int c = 4;
        positive_numbers: for (int i = 7; c < n; i += 2) {
            for (int j = 0; j < c; j++)
                if (i % primes[j] == 0)
                    continue positive_numbers;
            primes[c++] = i;
        }
        return primes[n - 1];
    }

    static int alternative(int n) {
        int i = 3;
        positive_numbers: for (int c = 1; c < n; i += 2) {
            for (int j = 2; j <= Math.sqrt(i); j++)
                if (i % j == 0)
                    continue positive_numbers;
            c++;
        }
        return i - 2;
    }

}
