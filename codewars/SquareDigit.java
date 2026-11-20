// see: https://www.codewars.com/kata/546e2562b03326a88e000020

import java.util.Arrays;
import java.util.stream.Collectors;

public class SquareDigit {

    public int squareDigits(int n) {
        var digits = String.valueOf(n).split("");
        var str = Arrays.stream(digits)
                .map(Integer::parseInt)
                .map(i -> i * i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
        return Integer.parseInt(str);
    }

}
