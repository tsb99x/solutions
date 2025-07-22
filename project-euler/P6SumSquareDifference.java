package euler;

// https://projecteuler.net/problem=6
// https://en.wikipedia.org/wiki/Square_pyramidal_number
// https://en.wikipedia.org/wiki/Difference_of_two_squares

// <p>The sum of the squares of the first ten natural numbers is,</p>
// $$1^2 + 2^2 + ... + 10^2 = 385$$
// <p>The square of the sum of the first ten natural numbers is,</p>
// $$(1 + 2 + ... + 10)^2 = 55^2 = 3025$$
// <p>Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is $3025 - 385 = 2640$.</p>
// <p>Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.</p>

public class P6SumSquareDifference {

    public static void main(String[] args) {
        System.out.println(solution(100)); // 25164150
//        Runner.timed(() -> solution(100), 10_000, 10);
        System.out.println(iterative(100)); // 25164150
//        Runner.timed(() -> iterative(100), 10_000, 10);
    }

    static long solution(long n) {
        long sumOfNs = n * (n + 1) / 2;
        long squareOfSum = sumOfNs * sumOfNs;
        long sumOfSquares = n * (n + 1) * (2 * n + 1) / 6;
        return squareOfSum - sumOfSquares;
    }

    static long iterative(long n) {
        long diff = 0;
        for (long i = 1; i <= n; i++)
            diff += i * i * (i - 1);
        return diff;
    }

}
