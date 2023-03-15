package euler;

// https://projecteuler.net/problem=9

// <p>A Pythagorean triplet is a set of three natural numbers, <var>a</var> &lt; <var>b</var> &lt; <var>c</var>, for which,</p>
// <div class="center"> <var>a</var><sup>2</sup> + <var>b</var><sup>2</sup> = <var>c</var><sup>2</sup></div>
// <p>For example, 3<sup>2</sup> + 4<sup>2</sup> = 9 + 16 = 25 = 5<sup>2</sup>.</p>
// <p>There exists exactly one Pythagorean triplet for which <var>a</var> + <var>b</var> + <var>c</var> = 1000.<br />Find the product <var>abc</var>.</p>

public class P9SpecialPythagoreanTriplet {

    public static void main(String[] args) {
        System.out.println(solution()); // 31875000
    }

    static long solution() {
        for (long a = 1; a < 1000; a++) {
            for (long b = a + 1; b < 1000; b++) {
                // FIXME : c = 1000 - a - b, no need to loop this over
                for (long c = b + 1; c < 1000; c++) {
                    if (a * a + b * b == c * c && a + b + c == 1000) {
                        return a * b * c;
                    }
                }
            }
        }
        return 0;
    }

}
