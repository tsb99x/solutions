#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

BIN=/tmp/part_i
SRC=./part_i.c

RESET='\033[0;0m'
RED='\033[0;31m'
GREEN='\033[0;32m'
BOLD='\033[1;37m'

check () {
        echo -ne "${BOLD}Test${RESET} $1 => $2 "
        OUTPUT=$(echo "$1" | $BIN 2>&1) && [ "$OUTPUT" = "$2" ] || {
                echo -e "${RED}FAILED${RESET}, output: $OUTPUT"
                false
        }
        echo -e "${GREEN}PASSED${RESET}"
}

clang -g -Og -std=c99 \
        -Weverything -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o "$BIN" "$SRC"

gcc -g -Og -std=c99 \
        -Wall -Wextra -Wpedantic -Werror \
        -fsanitize=undefined,address \
        -fno-sanitize-recover=all \
        -o "$BIN" "$SRC"

clang-tidy $SRC 2> /dev/null

check '>' 2
check '^>v<' 4
check '^v^v^v^v^v' 2
echo 'All tests are completed and passed!'

ANSWER="$($BIN < ./input 2>&1)" && echo "Answer for input: $ANSWER" || {
        echo -e "${RED}Program has exited with non-zero code!${RESET}"
        echo "Output: $ANSWER"
}
