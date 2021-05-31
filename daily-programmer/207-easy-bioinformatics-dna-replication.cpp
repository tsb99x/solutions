/*

    https://www.reddit.com/r/dailyprogrammer/comments/2zyipu/20150323_challenge_207_easy_bioinformatics_1_dna/

    [2015-03-23] Challenge #207 [Easy] Bioinformatics 1: DNA Replication

    For this week my theme is bioinformatics, I hope you enjoy the taste of the field through these challenges.

    Description

    DNA - deoxyribonucleic acid - is the building block of every organism.
    It contains information about hair color, skin tone, allergies, and more.
    It's usually visualized as a long double helix of base pairs.
    DNA is composed of four bases - adenine, thymine, cytosine, guanine - paired as follows: A-T and G-C.

    Meaning: on one side of the strand there may be a series of bases

        A T A A G C

    And on the other strand there will have to be

        T A T T C G

    It is your job to generate one side of the DNA strand and output the two DNA strands.
    Your program should take a DNA sequence as input and return the complementary strand.

    Input

        A A T G C C T A T G G C

    Output

        A A T G C C T A T G G C
        T T A C G G A T A C C G

    Extra Challenge

    Three base pairs make a codon.
    These all have different names based on what combination of the base pairs you have.
    A handy table can be found here.
    The string of codons starts with an ATG (Met) codon ends when a STOP codon is hit.

    For this part of the challenge, you should implement functionality for translating the DNA to a protein sequence based on the codons, recalling that every generated DNA strand starts with a Met codon and ends with a STOP codon.
    Your program should take a DNA sequence and emit the translated protein sequence, complete with a STOP at the terminus.

    Input

        A T G T T T C G A G G C T A A

    Output

        A T G T T T C G A G G C T A A
        Met Phe Arg Gly STOP

    Credit

    Thanks to /u/wickys for the submission.
    If you have your own idea for a challenge, submit it to r/DailyProgrammer_Ideas, and there's a good chance we'll post it.

*/

#include "utils/utils.hpp"

#include <iostream>
#include <unordered_map>

using namespace std;
using namespace utils;

enum mode {
    complement,
    identify
};

mode parse_mode(const vector<string> &args)
{
    if (args.size() != 2)
        throw runtime_error("Failed to obtain mode arg, use:\n"
                            "\t'-c' to complement\n"
                            "\t'-i' to identify");

    if (args[1] == "-c")
        return complement;
    if (args[1] == "-i")
        return identify;

    throw runtime_error("Failed to parse mode of '"s + args[1] + "'");
}

string read_input()
{
    string res;

    cout << "SRC > ";
    getline(cin, res);

    return res;
}

string validate(const string &src)
{
    size_t not_valid_idx = src.find_first_not_of("ACGT ");

    if (not_valid_idx != string::npos)
        throw runtime_error(
                "Input must consist of 'A', 'C', 'G', 'T' and spaces, but found '"s +
                src[not_valid_idx] + "'");

    return src;
}

vector<char> convert_input(const string &src)
{
    vector<char> res;

    for (const char ch : src)
        if (ch != ' ')
            res.push_back(ch);

    return res;
}

char lookup_base_complement(const char base)
{
    switch (base) {
    case 'A':
        return 'T';
    case 'T':
        return 'A';
    case 'G':
        return 'C';
    case 'C':
        return 'G';
    default:
        throw runtime_error("Failed to complement base of '"s + base + "'");
    }
}

vector<char> do_complement(const vector<char> &bases)
{
    vector<char> res;
    res.reserve(bases.size());

    for (const char base : bases)
        res.push_back(lookup_base_complement(base));

    return res;
}

unordered_map<string, string> codon_lookup = {

        {"TTT", "Phe"},
        {"TTC", "Phe"},

        {"TTA", "Leu"},
        {"TTG", "Leu"},
        {"CTT", "Leu"},
        {"CTC", "Leu"},
        {"CTA", "Leu"},
        {"CTG", "Leu"},

        {"ATT", "Ile"},
        {"ATC", "Ile"},
        {"ATA", "Ile"},

        {"ATG", "Met"},

        {"GTT", "Val"},
        {"GTC", "Val"},
        {"GTA", "Val"},
        {"GTG", "Val"},

        {"TCT", "Ser"},
        {"TCC", "Ser"},
        {"TCA", "Ser"},
        {"TCG", "Ser"},

        {"CCT", "Pro"},
        {"CCC", "Pro"},
        {"CCA", "Pro"},
        {"CCG", "Pro"},

        {"ACT", "Thr"},
        {"ACC", "Thr"},
        {"ACA", "Thr"},
        {"ACG", "Thr"},

        {"GCT", "Ala"},
        {"GCC", "Ala"},
        {"GCA", "Ala"},
        {"GCG", "Ala"},

        {"TAT", "Tyr"},
        {"TAC", "Tyr"},

        {"TAA", "STOP"},
        {"TAG", "STOP"},

        {"CAT", "His"},
        {"CAC", "His"},

        {"CAA", "Gln"},
        {"CAG", "Gln"},

        {"AAT", "Asn"},
        {"AAC", "Asn"},

        {"AAA", "Lys"},
        {"AAG", "Lys"},

        {"GAT", "Asp"},
        {"GAC", "Asp"},

        {"GAA", "Glu"},
        {"GAG", "Glu"},

        {"TGT", "Cys"},
        {"TGC", "Cys"},

        {"TGA", "STOP"},

        {"TGG", "Trp"},

        {"CGT", "Arg"},
        {"CGC", "Arg"},
        {"CGA", "Arg"},
        {"CGG", "Arg"},

        {"AGT", "Ser"},
        {"AGC", "Ser"},

        {"AGA", "Arg"},
        {"AGG", "Arg"},

        {"GGT", "Gly"},
        {"GGC", "Gly"},
        {"GGA", "Gly"},
        {"GGG", "Gly"},

};

vector<string> do_convert(const vector<char> &bases)
{
    vector<string> res;

    string codon_bases;
    for (const char ch : bases) {
        codon_bases += ch;
        if (codon_bases.length() == 3) {
            res.push_back(codon_lookup[codon_bases]);
            codon_bases.clear();
        }
    }

    if (!codon_bases.empty())
        res.emplace_back("...");

    return res;
}

int main(int argc,
         const char *argv[])
{
    try {

        mode m = parse_mode(args(argc, argv));
        string src = validate(read_input());
        vector<char> bases = convert_input(src);

        string res;
        if (m == complement)
            res = join(do_complement(bases), " ");
        else
            res = join(do_convert(bases), " ");

        cout << "RES > " << res;

    } catch (const exception &e) {
        cerr << e.what() << endl;
    }
}
