module Utils (input) where

import System.IO (hFlush, stdout)

input :: String -> IO String
input s = putStr s >> hFlush stdout >> getLine
