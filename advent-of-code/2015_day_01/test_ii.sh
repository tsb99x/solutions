#!/bin/bash -eu

BIN=/tmp/part_ii
SRC=./part_ii.c

check () {
        echo -n "Test $1 => $2 "
        OUTPUT=$(echo "$1" | $BIN || true)
        [ "$OUTPUT" = "$2" ] \
                || (echo "FAILED, output: $OUTPUT" && false)
        echo "PASSED"
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
