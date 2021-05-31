{-

    https://www.reddit.com/r/dailyprogrammer/comments/qr0hg/3102012_challenge_22_easy/

    [3/10/2012] Challenge #22 [easy]

    Write a program that will compare two lists, and append any elements in the second list that doesn't exist in the first.

    input: ["a", "b", "c", 1, 4], ["a", "x", 34, "4"]

    output: ["a", "b", "c", 1, 4, "x", 34, "4"]

-}

-- This spec would not work for Haskell homogenous lists, so I am chaning it a bit

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))
import Data.List (nub)

main :: IO ()
main = void . runTestTT $ test
    [ ["a", "b", "c", "1", "4", "x", "34"] ~=? appendUnique ["a", "b", "c", "1", "4"] ["a", "x", "34", "4"]
    ]

appendUnique :: [String] -> [String] -> [String]
appendUnique xs ys = nub $ xs ++ ys
