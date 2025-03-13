#!/bin/bash -eu

BIN=/tmp/part_ii
SRC=./part_ii.c

RESET='\033[0;0m'
RED='\033[0;31m'
GREEN='\033[0;32m'
BOLD='\033[1;37m'

check () {
        echo -ne "${BOLD}Test${RESET} $1 => $2 "
        OUTPUT=$($BIN "$1" 2>&1) && [ "$OUTPUT" = "$2" ] || {
                echo -e "${RED}FAILED${RESET}, output: $OUTPUT"
                false
        }
        echo -e "${GREEN}PASSED${RESET}"
}

clang -g -Og -std=c99 \
        -Weverything -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o "$BIN" "$SRC" \
        -lcrypto

gcc -g -Og -std=c99 \
        -Wall -Wextra -Wpedantic -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o "$BIN" "$SRC" \
        -lcrypto

clang-tidy $SRC 2> /dev/null
cppcheck --enable=all --suppress=missingIncludeSystem $SRC

ANSWER="$($BIN ckczppom 2>&1)" && echo "Answer for input: $ANSWER" || {
        echo -e "${RED}Program has exited with non-zero code!${RESET}"
        echo "Output: $ANSWER"
}
