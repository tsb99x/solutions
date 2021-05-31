# Programming Challenges

This is a repository with solutions to common programming problems.
Each solution exists in its own file.
A problem, which needs to be solved is described in the topmost comment.
This includes link to original excercise page.

## What are the Rules?

- No build system should be used, except for compiler
- No package manager is allowed, e.g. no use of a non-standard modules
- There should be only one file per solution
- Only one utility file is allowed per language

## How to launch a solution?

To launch a solution:

For `.hs` files:

    sudo apt install haskell-platform
    runghc 001-easy-input.hs

For `.c` files:

    sudo apt install gcc
    gcc 001-easy-input.c -ansi -Wall -Wextra -Werror -o /tmp/a.out && /tmp/a.out

For `.cpp` files:

    sudo apt install gcc
    g++ 001-hard-guessing-game-reverse.cpp -std=c++17 -o /tmp/a.out && /tmp/a.out

For `.py` files:

    sudo apt install python3
    python3 001-easy-input.py

For `.java` files:

    sudo apt install openjdk-14-jdk
    java ./001-hard-guessing-game-reverse.java
