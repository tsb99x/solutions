#!/bin/bash -e

BIN=/tmp/part_ii
SRC=./part_ii.c

check () {
        echo "$1" | $BIN | grep -qxe "$2" && return 0 \
                || echo "check for $1 -> $2 has failed!" && return 1
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

check ')' 1
check '()())' 5
echo 'All tests are completed and passed!'

echo "Answer for input: $($BIN < ./input)"
