package euler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Runner;

// https://projecteuler.net/problem=5

/*
 * <p>2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.</p>
 * <p>What is the smallest positive number that is <dfn title="divisible with no remainder">evenly divisible</dfn> by all of the numbers from 1 to 20?</p>
 */

public class P5SmallestMultiple {

    public static void main(String[] args) {
        // System.out.println(bruteforce()); // 232792560
        // Runner.timed(P5SmallestMultiple::bruteforce, 100);
        // System.out.println(leastCommonMultiple()); // 232792560
        Runner.timed(P5SmallestMultiple::leastCommonMultiple, 1_000_000, 10);
        System.out.println("done!");
        Runner.timed(() -> lcm2(20), 1_000_000, 10);
    }

    static int bruteforce() {
        positive_integers: for (int n = 20; n < Integer.MAX_VALUE; n += 20) {
            for (int i = 19; i > 10; i--) {
                if (n % i != 0) {
                    continue positive_integers;
                }
            }
            return n;
        }
        return 0;
    }

    static long leastCommonMultiple() {
        var powerOfPrimes = new HashMap<Integer, Long>();
        for (int n = 2; n <= 20; n++) {
            countElements(primeFactors(n))
                    .forEach((k, v) -> powerOfPrimes.merge(k, v, Long::max));
        }

        long res = 1;
        for (var kv : powerOfPrimes.entrySet()) {
            res *= (long) Math.pow(kv.getKey(), kv.getValue());
        }
        return res;
    }

    static long lcm2(int n) {
        var powerOfPrimes = new HashMap<Integer, Integer>();
        for (int i = 2; i <= n; i++) {
            primeFactorsMap(n)
                    .forEach((k, v) -> powerOfPrimes.merge(k, v, Integer::max));
        }

        long res = 1;
        for (var kv : powerOfPrimes.entrySet()) {
            res *= (long) Math.pow(kv.getKey(), kv.getValue());
        }
        return res;
    }

    static Map<Integer, Long> countElements(List<Integer> list) {
        var res = new HashMap<Integer, Long>();
        for (var el : list) {
            res.merge(el, 1L, Long::sum);
        }
        return res;
//        return list.stream()
//                .collect(Collectors.groupingBy(Function.identity(),
//                        Collectors.counting()));
    }

    static List<Integer> primeFactors(int number) {
        var res = new ArrayList<Integer>();
        for (int n = 2; n <= Math.sqrt(number); n++) {
            if (number % n == 0) {
                res.add(n);
                number /= n--;
            }
        }
        res.add(number);
        return res;
    }

    static Map<Integer, Integer> primeFactorsMap(int number) {
        var res = new HashMap<Integer, Integer>();
        for (int n = 2; n <= Math.sqrt(number); n++) {
            if (number % n == 0) {
                res.merge(n, 1, Integer::sum);
                number /= n--;
            }
        }
        res.merge(number, 1, Integer::sum);
        return res;
    }

}
