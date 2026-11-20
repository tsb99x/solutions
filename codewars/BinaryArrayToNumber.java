// see: https://www.codewars.com/kata/578553c3a1b8d5c40300037c

import java.util.List;

public class BinaryArrayToNumber {

    public static int ConvertBinaryArrayToInt(List<Integer> binary) {
        int res = 0;
        for (var x : binary) {
            res = 2 * res + x;
        }
        return res;
    }
}
