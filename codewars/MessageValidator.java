// see: https://www.codewars.com/kata/5fc7d2d2682ff3000e1a3fbc

import java.util.InputMismatchException;
import java.util.Scanner;

public class MessageValidator {

    public static boolean isAValidMessage(String message) {
        var scanner = new Scanner(message);
        try {
            var count = scanner.nextInt();
        } catch (InputMismatchException ex) {
            return false;
        }
        return true;
    }

}
