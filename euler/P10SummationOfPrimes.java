package euler;

import common.Runner;

public class P10SummationOfPrimes {

    public static void main(String[] args) {
        System.out.println(solution(10)); // 17
        System.out.println(solution2(2_000_000)); // 142913828922
        Runner.timed(() -> solution(2_000_000), 10, 10);
        Runner.timed(() -> solution2(2_000_000), 10, 10);
    }

    static long solution(long n) {
        long sum = 0;
        iterate_naturals: for (long i = 2; i < n; i++) {
            for (long j = 2; j <= Math.sqrt(i); j++)
                if (i % j == 0)
                    continue iterate_naturals;
            sum += i;
        }
        return sum;
    }

    static long solution2(long n) {
        long sum = 0;
        for (long i = 2; i < n; i++)
            if (isPrime(i))
                sum += i;
        return sum;
    }

    static boolean isPrime(long n) {
        for (long i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return false;
        return true;
    }

}
