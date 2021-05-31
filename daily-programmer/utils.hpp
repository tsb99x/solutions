#pragma once

#include <iostream>
#include <optional>
#include <sstream>
#include <vector>

// alt. to Python's input

std::string input(
    const std::string &prompt
) {
    std::string res;
    std::cout << prompt << std::flush;
    std::getline(std::cin, res);
    return res;
}

// alt. for Java's String.split

std::vector<std::string> split(
    const std::string &orig,
    const char sep
) {
    std::vector<std::string> res;
    std::stringstream ss(orig);
    std::string tok;
    while (std::getline(ss, tok, sep))
        res.push_back(tok);
    return res;
}

// alt. for Haskell's readMaybe

template<typename T>
std::optional<T> read_opt(
    const std::string& orig
);

template<>
std::optional<int> read_opt(
    const std::string& str
) {
    int res;
    size_t chars_processed;
    try {
        res = std::stoi(str, &chars_processed);
    } catch (const std::exception &ex) {
        return std::nullopt;
    }
    if (chars_processed != str.size())
        return std::nullopt;
    return res;
}
