{-

    https://www.reddit.com/r/dailyprogrammer/comments/pxs2x/2202012_challenge_12_easy/

    [2/20/2012] Challenge #12 [easy]

    Write a small program that can take a string:

    "hi!"

    and print all the possible permutations of the string:

    "hi!"

    "ih!"

    "!hi"

    "h!i"

    "i!h"

    etc...

    thanks to hewts for this challenge!

-}

import Data.List (delete)

main :: IO ()
main = mapM_ putStrLn . permutations =<< getLine

{- While there is `permutations` in `Data.List`, here is another variant -}

permutations :: String -> [String]
permutations [] = [[]]
permutations str = concatMap permute str
    where permute ch = map (ch:) $ permutations $ delete ch str
