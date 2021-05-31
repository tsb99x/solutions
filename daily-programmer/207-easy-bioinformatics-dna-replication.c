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

#include "utils/panic.h"

#define BUF_SIZE 255

void complement(const char *src_strand, char *dst_buf);
void identify(const char *src_strand, char *dst_buf);
char lookup_base_compl(const char base);

enum mode { compl, ident };

enum mode parse_mode(const char *const arg)
{
    if (!strcmp(arg, "-c")) {
        return compl;
    } else if (!strcmp(arg, "-i")) {
        return ident;
    } else {
        panic_m("Failed to parse mode of '%s'", arg);
    }
}

int main(int argc, char **argv)
{
    if (argc < 2)
        panic_m("Failed to obtain mode arg, use:\n"
                "\t'-c' to complement\n"
                "\t'-i' to identify");

    enum mode mode = parse_mode(argv[1]);

    printf("SRC> ");
    char input_buf[BUF_SIZE];
    if (!fgets(input_buf, sizeof input_buf, stdin))
        panic_m("Failed to acquire input");
    input_buf[strcspn(input_buf, "\n")] = 0;

    char dst_buf[BUF_SIZE];
    if (mode == compl) {
        complement(input_buf, dst_buf);
    } else {
        identify(input_buf, dst_buf);
    }
    printf("RES> %s", dst_buf);
}

// Base challenge

void complement(const char *src_strand, char *dst_buf)
{
    while (*src_strand != '\0') {
        *dst_buf = lookup_base_compl(*src_strand);

        src_strand++;
        dst_buf++;
    }

    *dst_buf = '\0';
}

char lookup_base_compl(const char base)
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
    case ' ':
        return ' ';
    default:
        panic_m("Failed to complement base of '%c'", base);
    }
}

// Extra part next

struct lookup_item_t {
    const char *const bases;
    const char *const codon;
};

// This is just a tree, think about it!

struct lookup_item_t codon_lookup[] = {

    {"TTT", "Phe"},  {"TTC", "Phe"},

    {"TTA", "Leu"},  {"TTG", "Leu"},  {"CTT", "Leu"}, {"CTC", "Leu"},
    {"CTA", "Leu"},  {"CTG", "Leu"},

    {"ATT", "Ile"},  {"ATC", "Ile"},  {"ATA", "Ile"},

    {"ATG", "Met"},

    {"GTT", "Val"},  {"GTC", "Val"},  {"GTA", "Val"}, {"GTG", "Val"},

    {"TCT", "Ser"},  {"TCC", "Ser"},  {"TCA", "Ser"}, {"TCG", "Ser"},

    {"CCT", "Pro"},  {"CCC", "Pro"},  {"CCA", "Pro"}, {"CCG", "Pro"},

    {"ACT", "Thr"},  {"ACC", "Thr"},  {"ACA", "Thr"}, {"ACG", "Thr"},

    {"GCT", "Ala"},  {"GCC", "Ala"},  {"GCA", "Ala"}, {"GCG", "Ala"},

    {"TAT", "Tyr"},  {"TAC", "Tyr"},

    {"TAA", "STOP"}, {"TAG", "STOP"},

    {"CAT", "His"},  {"CAC", "His"},

    {"CAA", "Gln"},  {"CAG", "Gln"},

    {"AAT", "Asn"},  {"AAC", "Asn"},

    {"AAA", "Lys"},  {"AAG", "Lys"},

    {"GAT", "Asp"},  {"GAC", "Asp"},

    {"GAA", "Glu"},  {"GAG", "Glu"},

    {"TGT", "Cys"},  {"TGC", "Cys"},

    {"TGA", "STOP"},

    {"TGG", "Trp"},

    {"CGT", "Arg"},  {"CGC", "Arg"},  {"CGA", "Arg"}, {"CGG", "Arg"},

    {"AGT", "Ser"},  {"AGC", "Ser"},

    {"AGA", "Arg"},  {"AGG", "Arg"},

    {"GGT", "Gly"},  {"GGC", "Gly"},  {"GGA", "Gly"}, {"GGG", "Gly"},

};

// Much better way would be to process simple list, that is.

#define count_m(X) sizeof X / sizeof X[0]

void convert_to_codon(const char *const bases, char **dst_buf)
{
    for (size_t i = 0; i < count_m(codon_lookup); i++) {
        if (!strncmp(bases, codon_lookup[i].bases, 3)) {
            size_t codon_len = strlen(codon_lookup[i].codon);

            strncpy(*dst_buf, codon_lookup[i].codon, codon_len);
            *dst_buf += codon_len;

            **dst_buf = ' ';
            *dst_buf += 1;

            return;
        }
    }

    panic_m("Failed to find codon for bases of '%s'", bases);
}

void identify(const char *src_strand, char *dst_buf)
{
    char bases_buf[] = "###";
    int bases_index = 0;

    while (*src_strand != '\0') {
        if (*src_strand != ' ') {
            bases_buf[bases_index] = *src_strand;
            bases_index++;

            if (bases_index == 3) {
                convert_to_codon(bases_buf, &dst_buf);
                bases_index = 0;
            }
        }

        src_strand++;
    }

    if (bases_index != 0) {
        printf("%d of bases at the end of strand does not form codon\n",
               bases_index);
    }

    *dst_buf = '\0';
}
