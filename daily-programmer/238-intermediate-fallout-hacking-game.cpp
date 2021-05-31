/*

    https://www.reddit.com/r/dailyprogrammer/comments/3qjnil/20151028_challenge_238_intermediate_fallout/

    [2015-10-28] Challenge #238 [Intermediate] Fallout Hacking Game

    Description

    The popular video games Fallout 3 and Fallout: New Vegas have a computer "hacking" minigame where the player must correctly guess the correct password from a list of same-length words.
    Your challenge is to implement this game yourself.

    The game operates similarly to the classic board game [Mastermind](http://en.wikipedia.org/wiki/Mastermind_(board_game)).
    The player has only 4 guesses and on each incorrect guess the computer will indicate how many letter positions are correct.

    For example, if the password is MIND and the player guesses MEND, the game will indicate that 3 out of 4 positions are correct (M_ND).
    If the password is COMPUTE and the player guesses PLAYFUL, the game will report 0/7.
    While some of the letters match, they're in the wrong position.

    Ask the player for a difficulty (very easy, easy, average, hard, very hard), then present the player with 5 to 15 words of the same length.
    The length can be 4 to 15 letters.
    More words and letters make for a harder puzzle.
    The player then has 4 guesses, and on each incorrect guess indicate the number of correct positions.

    Here's an example game:

        Difficulty (1-5)? 3
        SCORPION
        FLOGGING
        CROPPERS
        MIGRAINE
        FOOTNOTE
        REFINERY
        VAULTING
        VICARAGE
        PROTRACT
        DESCENTS
        Guess (4 left)? migraine
        0/8 correct
        Guess (3 left)? protract
        2/8 correct
        Guess (2 left)? croppers
        8/8 correct
        You win!

    You can draw words from our favorite dictionary file: enable1.txt.
    Your program should completely ignore case when making the position checks.

    There may be ways to increase the difficulty of the game, perhaps even making it impossible to guarantee a solution, based on your particular selection of words.
    For example, your program could supply words that have little letter position overlap so that guesses reveal as little information to the player as possible.

    Credit

    This challenge was created by user /u/skeeto.
    If you have any challenge ideas please share them on r/dailyprogrammer_ideas and there's a good chance we'll use them.

*/

#include "utils/utils.hpp"

#include <algorithm>
#include <random>
#include <chrono>

using namespace std;
using namespace chrono;
using namespace utils;

int get_difficulty()
{
    int res;

    cout << "Difficulty (0-4)? ";
    cin >> res;

    if (res < 0 || res > 4)
        throw runtime_error("Input difficulty must be in 0-4 range");

    return res;
}

struct range {
    int from;
    int to;
};

range diff_map[] = {
        {4,  5},
        {6,  8},
        {9,  10},
        {11, 12},
        {13, 15}
};

int get_rand_from_range(const range &range,
                        default_random_engine &dre)
{
    uniform_int_distribution range_dist(range.from, range.to);
    return range_dist(dre);
}

vector<string> filter_words(const vector<string> &dict,
                            const size_t length,
                            default_random_engine &dre)
{
    vector<string> res;

    for (const auto &word: dict)
        if (word.length() == length)
            res.push_back(word);

    shuffle(res.begin(), res.end(), dre);

    return res;
}

vector<string> select_words(const vector<string> &words,
                            const int count)
{
    vector<string> res;

    for (int i = 0; i < count && !words.empty(); i++)
        res.push_back(words[i]);

    return res;
}

void output_words(const vector<string> &words)
{
    for (const auto &word: words)
        cout << word << endl;
}

int guess(const int guesses_left,
          const string &word)
{
    cout << "Guess (" << guesses_left << " left)? ";

    string str;
    cin >> str;

    if (str == word) {
        cout << word.length() << "/" << word.length() << " correct" << endl;
        return -1;
    }

    int counter = 0;
    for (size_t i = 0; i < word.length() && i < str.length(); i++)
        if (str[i] == word[i])
            counter++;

    cout << counter << "/" << word.length() << " correct" << endl;
    return guesses_left - 1;
}

void try_guess(const string &word,
               int guesses_left)
{
    while (guesses_left > 0)
        guesses_left = guess(guesses_left, word);

    if (guesses_left == -1)
        cout << "You win!" << endl;
    else
        cout << "You lose!" << endl;
}

int main()
{
    try {

        time_point tp = system_clock::now();
        default_random_engine gen(tp.time_since_epoch().count());

        vector<string> dict = read_file("res/enable1.txt");
        int diff_val = get_difficulty();

        int word_length = get_rand_from_range(diff_map[diff_val], gen);
        vector<string> filtered = filter_words(dict, word_length, gen);

        int word_count = get_rand_from_range(diff_map[diff_val], gen);
        vector<string> words = select_words(filtered, word_count);
        output_words(words);

        string word = words[get_rand_from_range({0, word_count}, gen)];
        try_guess(word, 4);

    } catch (const exception &e) {

        cerr << e.what();

    }
}
