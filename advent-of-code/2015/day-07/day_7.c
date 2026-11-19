/*
        https://adventofcode.com/2015/day/7
        --- Day 7: Some Assembly Required ---

        just signal
        AND -- bitwise and
        LSHIFT -- shift to left by n bits

        $ gcc -O2 -ansi \
                -Wall -Wextra -Wpedantic -Werror \
                -fsanitize=address \
                -o advent_of_code_2015_7{,.c} \
                && ./advent_of_code_2015_7
*/

#include <assert.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

int emulate(const char *circuit)
{
        uint16_t wires[26] = {0};
        int v, chs;
        char wi1, wi2, wo;

        /*int i;*/

        while (*circuit != '\0') {
                if (sscanf(circuit, "%c AND %c -> %c %n", &wi1, &wi2, &wo,
                           &chs) == 3) {
                        /*printf("AND\n");*/
                        wires[wo - 'a'] = wires[wi1 - 'a'] & wires[wi2 - 'a'];
                } else if (sscanf(circuit, "%d -> %c %n", &v, &wo, &chs) == 2) {
                        /*printf("SET\n");*/
                        wires[wo - 'a'] = v;
                } else if (sscanf(circuit, "%c OR %c -> %c %n", &wi1, &wi2, &wo,
                                  &chs) == 3) {
                        /*printf("OR\n");*/
                        wires[wo - 'a'] = wires[wi1 - 'a'] | wires[wi2 - 'a'];
                } else if (sscanf(circuit, "%c LSHIFT %d -> %c %n", &wi1, &v,
                                  &wo, &chs) == 3) {
                        /*printf("LSHIFT\n");*/
                        wires[wo - 'a'] = wires[wi1 - 'a'] << v;
                } else if (sscanf(circuit, "%c RSHIFT %d -> %c %n", &wi1, &v,
                                  &wo, &chs) == 3) {
                        /*printf("RSHIFT\n");*/
                        wires[wo - 'a'] = wires[wi1 - 'a'] >> v;
                } else if (sscanf(circuit, "NOT %c -> %c %n", &wi1, &wo,
                                  &chs) == 2) {
                        /*printf("NOT\n");*/
                        wires[wo - 'a'] = ~wires[wi1 - 'a'];
                } else {
                        abort();
                }
                circuit += chs;
                /* printf("advance by %d, current symbol is %d\n", chs,
                 * *circuit); */
        }

        /*
                for (i = 0; i < 26; i++)
                        printf("wire %c is %d\n", 'a' + i, wires[i]);
        */

        return wires[0];
}

int main(void)
{
        const char *circuit = "123 -> x\n"
                              "456 -> y\n"
                              "x AND y -> d\n"
                              "x OR y -> e\n"
                              "x LSHIFT 2 -> f\n"
                              "y RSHIFT 2 -> g\n"
                              "NOT x -> h\n"
                              "NOT y -> a";
        assert(emulate(circuit) == 65079);
        return 0;
}
