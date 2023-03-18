package codewars;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CW1 {

    public static void main(String[] args) {
        int[] nums = { 206847684, 1056521, 7, 17, 1901, 21104421, 7, 1, 35521,
                1, 7781 };
        /*
         * var r = IntStream.of(nums)
         * .boxed()
         * .collect(Collectors.partitioningBy(n -> n % 2 == 0));
         */
        /*
         * return r.get(true).size() == 1
         * ? r.get(true).get(0)
         * : r.get(false).get(0);
         */
        System.out.println(ConvertBinaryArrayToInt(Arrays.asList(0, 0, 0, 1)));

        System.out.println(isAValidMessage("code4hello5"));

        System.out.println(Integer.parseInt(""));
    }

    static boolean isAValidMessage(String message) {
        var scanner = new Scanner(message);
        try {
            var count = scanner.nextInt();
        } catch (InputMismatchException ex) {
            return false;
        }
        return true;
    }

    public static int ConvertBinaryArrayToInt(List<Integer> binary) {
        int res = 0;
        int npow = 1;
        int i = binary.size();
        while (i-- > 0) {
            res += binary.get(i) * npow;
            npow *= 2;
        }
        return res;
    }

}
