{-

    https://www.reddit.com/r/dailyprogrammer/comments/qp3ub/392012_challenge_21_easy/

    [3/9/2012] Challenge #21 [easy]

    Input: a number

    Output: the next higher number that uses the same set of digits.

-}

-- Narayana Pandita Method

import Test.HUnit ((~=?), runTestTT, Testable(test))
import Control.Monad (void)
import Data.List (elemIndices, tails)

main :: IO ()
main = void $ runTestTT $ test
    [ ""    ~=? nextPermutation ""
    , "132" ~=? nextPermutation "123"
    , "213" ~=? nextPermutation "132"
    , "231" ~=? nextPermutation "213"
    , "312" ~=? nextPermutation "231"
    , "321" ~=? nextPermutation "312"
    , "321" ~=? nextPermutation "321"
    ]

nextPermutation :: String -> String
nextPermutation str = formPermutation px sx
    where px = take (length str - length sx) str
          sx = head . filter isSorted $ tails str
          isSorted xs = and . zipWith (>=) xs $ tail xs

formPermutation :: String -> String -> String
formPermutation [] sx = sx
formPermutation px sx = prefix ++ successor:newSuffix
    where newSuffix = reverse $ replaceLast successor pivot sx
          successor = last $ takeWhile (>= pivot) sx
          (prefix, pivot) = (init px, last px)

replaceLast :: Char -> Char -> String -> String
replaceLast old new haystack = xs ++ new:tail ys
    where (xs, ys) = splitAt index haystack
          index = last . elemIndices old $ haystack
