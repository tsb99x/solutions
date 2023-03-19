package challenges;

import java.io.Console;

public class HardGuessingGameReverse {

    static final Console CON = System.console();

    public static void main(String[] args) {
        CON.format("""
                You may pick a number in [0, 100]
                Input [l] for lower, [h] for higher, [i] for it
                """);
        var iterations = guessNumber();
        CON.format("PC won in %d iterations%n", iterations);
    }

    static int guessNumber() {
        var rangeMin = 0;
        var rangeMax = 100;

        for (var iterations = 1;; iterations++) {
            var guess = (rangeMin + rangeMax) / 2;
            var answer = tryGuess(guess);

            if (answer == 'l') {
                rangeMax = guess;
            }
            if (answer == 'h') {
                rangeMin = guess;
            }
            if (answer == 'i') {
                return iterations;
            }

            if (rangeMin == rangeMax) {
                throw new IllegalArgumentException("Cheater! You re-picked the number in the middle of game");
            }
        }
    }

    static char tryGuess(int guess) {
        while (true) {
            var in = CON.readLine("Your number is %d? > ", guess);
            if ("l".equals(in) || "h".equals(in) || "i".equals(in)) {
                return in.charAt(0);
            }
            CON.format("Invalid input, please select one of [l], [h] or [i]%n");
        }
    }

}
