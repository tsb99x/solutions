{-

    https://www.reddit.com/r/dailyprogrammer/comments/reago/3262012_challenge_30_easy/

    [3/26/2012] Challenge #30 [easy]

    Write a program that takes a list of integers and a target number and determines if any two integers in the list sum to the target number.
    If so, return the two numbers.
    If not, return an indication that no such integers exist.

    ---

    Edit : Note the "Intermediate" challenge #30 has been changed. Thank you!

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))
import Data.List (tails)

main :: IO ()
main = void . runTestTT $ test
    [ Nothing     ~=? anySumTo  1 []
    , Just (1, 2) ~=? anySumTo  3 [1, 2, 3, 4, 5]
    , Just (3, 5) ~=? anySumTo  8 [4, 1, 3, 5, 0]
    , Nothing     ~=? anySumTo 10 [1, 2, 3, 4, 5]
    ]

anySumTo :: Int -> [Int] -> Maybe (Int, Int)
anySumTo target = headMaybe . filter sumEqTarget . concatMap zipWithHead . tails
    where zipWithHead []     = []
          zipWithHead (y:ys) = zip (repeat y) ys
          sumEqTarget (i, j) = target == i + j

headMaybe :: [a] -> Maybe a
headMaybe (x:_) = Just x
headMaybe []    = Nothing
