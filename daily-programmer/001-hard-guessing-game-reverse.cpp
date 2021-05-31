/*

    https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/

    [difficult] challenge #1

    we all know the classic "guessing game" with higher or lower prompts.
    lets do a role reversal; you create a program that will guess numbers between 1-100, and respond appropriately based on whether users say that the number is too high or too low.
    Try to make a program that can guess your number based on user input and great code!

*/

#include "utils.hpp"

using namespace std;

enum answer {
    LOWER,
    HIGHER,
    IT,
};

answer ask_if_guess_is_correct(
    const int guess
) {
    while (true) {
        string in = input("your number is " + to_string(guess) + "? > ");
        if (in == "l") return LOWER;
        if (in == "h") return HIGHER;
        if (in == "i") return IT;
        cout << "invalid input, please select one of [l], [h] or [i]" << endl;
    }
}

int main()
{
    cout << "you may pick a number in [0, 100]\n"
            "input [l] for lower, [h] for higher, [i] for it" << endl;

    int range_min = 0;
    int range_max = 100;

    for (int iter = 1;; iter++) {
        int guess = (range_min + range_max) / 2;
        answer ans = ask_if_guess_is_correct(guess);

        if (ans == LOWER) range_max = guess;
        if (ans == HIGHER) range_min = guess;
        if (ans == IT) {
            cout << "PC won in " << iter << " iterations" << endl;
            return EXIT_SUCCESS;
        }

        if (range_min == range_max) {
            cout << "cheater! you re-picked the number in the middle of the game" << endl;
            return EXIT_FAILURE;
        }
    }
}
