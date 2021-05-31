/*

    https://www.reddit.com/r/dailyprogrammer/comments/3pcb3i/20151019_challenge_237_easy_broken_keyboard/

    [2015-10-19] Challenge #237 [Easy] Broken Keyboard

    Description

    Help!
    My keyboard is broken, only a few keys work any more.
    If I tell you what keys work, can you tell me what words I can write?

    (You should use the trusty enable1.txt file, or /usr/share/dict/words to chose your valid English words from.)

    Input Description

    You'll be given a line with a single integer on it, telling you how many lines to read.
    Then you'll be given that many lines, each line a list of letters representing the keys that work on my keyboard.
    Example:

        3
        abcd
        qwer
        hjklo

    Output Description

    Your program should emit the longest valid English language word you can make for each keyboard configuration.

        abcd = bacaba
        qwer = ewerer
        hjklo = kolokolo

    Challenge Input

        4
        edcf
        bnik
        poil
        vybu

    Challenge Output

        edcf = deedeed
        bnik = bikini
        poil = pililloo
        vybu = bubby

    Credit

    This challenge was inspired by /u/ThinkinWithSand, many thanks!
    If you have any ideas, please share them on r/dailyprogrammer_ideas and there's a chance we'll use it.

*/

#include "utils/utils.hpp"

using namespace std;
using namespace utils;

vector<string> get_inputs()
{
    vector<string> res;

    cout << "Input line count: ";
    int n;
    cin >> n;

    for (int i = 0; i < n; i++) {
        cout << "Line #" << i + 1 << ": ";
        string str;
        cin >> str;
        res.push_back(str);
    }

    return res;
}

bool includes_every_char(const string &word,
                         const string &input)
{
    for (const char ch : word)
        if (input.find(ch) == string::npos)
            return false;

    return true;
}

const string *find_longest_match(const vector<string> &words,
                                 const string &input)
{
    const string *best = nullptr;

    for (const auto &word : words) {
        if (!includes_every_char(word, input))
            continue;
        if (best == nullptr || word.length() > best->length())
            best = &word;
    }

    return best;
}

struct assoc {
    const string &src;
    const string *best;
};

vector<assoc> pair_match(const vector<string> &words,
                         const vector<string> &inputs)
{
    vector<assoc> res;

    for (const string &input : inputs) {
        auto match = find_longest_match(words, input);
        res.push_back({input, match});
    }

    return res;
}

void output(const vector<assoc> &pairs,
            ostream &stream)
{
    for (const auto &elem : pairs) {
        stream << elem.src << " : ";
        if (elem.best == nullptr)
            stream << "no match exists";
        else
            stream << *elem.best;
        stream << endl;
    }
}

int main()
{
    try {

        auto words = read_file("res/enable1.txt");
        auto inputs = get_inputs();
        auto pairs = pair_match(words, inputs);
        output(pairs, cout);

    } catch (const exception &e) {

        cerr << e.what();

    }
}
