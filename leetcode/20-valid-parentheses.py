# URL: https://leetcode.com/problems/valid-parentheses/description/

mapping = {
    ")": "(",
    "]": "[",
    "}": "{",
}


class Solution:
    def isValid(self, s: str) -> bool:
        stack = []
        for c in s:
            if c not in mapping:
                stack.append(c)
            elif not stack or stack.pop() != mapping[c]:
                return False
        return not stack
