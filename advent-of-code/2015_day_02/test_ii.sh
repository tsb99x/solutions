#!/bin/bash -eu

BIN=/tmp/part_ii
SRC=./part_ii.c

check () {
        echo -n "Test $1 => $2 "
        OUTPUT=$(echo "$1" | $BIN 2>&1) && [ "$OUTPUT" = "$2" ] \
                || { echo "FAILED, output: $OUTPUT"; false; }
        echo "PASSED"
}

clang -Og -ansi \
        -Weverything -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o $BIN $SRC

gcc -Og -ansi \
        -Wall -Wextra -Wpedantic -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o $BIN $SRC

check '2x3x4' 34
check '1x1x10' 14
echo 'All tests are completed and passed!'

echo "Answer for input: $($BIN < ./input 2>&1)"
