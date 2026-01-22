class Solution:
    def lengthOfLastWord(self, s: str) -> int:
        s = s.rstrip()
        idx = s.rfind(" ", 0)
        return len(s) - (idx + 1 if idx != -1 else 0)
