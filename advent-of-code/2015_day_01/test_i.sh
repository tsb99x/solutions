#!/bin/bash -e

BIN=/tmp/part_i
SRC=./part_i.c

check () {
        [ "$(echo "$1" | $BIN)" = "$2" ] \
                || (echo "check for $1 -> $2 has failed!" && false)
}

clang -O2 -ansi \
        -Weverything -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o $BIN $SRC

gcc -O2 -ansi \
        -Wall -Wextra -Wpedantic -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o $BIN $SRC

check '(())' 0
check '()()' 0
check '(((' 3
check '(()(()(' 3
check '))(((((' 3
check '())' -1
check '))(' -1
check ')))' -3
check ')())())' -3
echo 'All tests are completed and passed!'

echo "Answer for input: $($BIN < ./input)"
