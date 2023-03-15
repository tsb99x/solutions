package euler;

// https://projecteuler.net/problem=4

/*
 * <p>A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.</p>
 * <p>Find the largest palindrome made from the product of two 3-digit numbers.</p>
 */

public class P4LargestPalindromeProduct {

    public static void main(String[] args) {
        System.out.println(procedural()); // 906609
    }

    static int procedural() {
        var max = 0;
        for (int i = 999; i > 99; i--) {
            for (int j = 999; j > 99; j--) {
                var mult = i * j;
                if (isPalindrome(mult) && mult > max) {
                    max = mult;
                }
            }
        }
        return max;
    }

    static boolean isPalindrome(int x) {
        var original = Integer.toString(x);
        var reverse = new StringBuilder(original).reverse().toString();
        return original.equals(reverse);
    }

}
