package codewars;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CW5 {

    public int squareDigits(int n) {
        var digits = String.valueOf(n).split("");
        var str = Arrays.stream(digits)
                .map(Integer::parseInt)
                .map(i -> i * i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
        return Integer.parseInt(str);
        //        Collections.swap(null, n, n);
    }

}
