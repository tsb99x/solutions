{-

    https://www.reddit.com/r/dailyprogrammer/comments/quli5/3132012_challenge_23_easy/

    [3/13/2012] Challenge #23 [easy]

    Input: a list

    Output: Return the two halves as different lists.

    If the input list has an odd number, the middle item can go to any of the list.

    Your task is to write the function that splits a list in two halves.

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))

main :: IO ()
main = void . runTestTT $ test
    [ ([1], [2, 3]) ~=? splitHalves [1, 2, 3]
    , ([1], [2])    ~=? splitHalves [1, 2]
    , ([], [1])     ~=? splitHalves [1]
    , ([], [])      ~=? splitHalves []
    ]

splitHalves :: [Int] -> ([Int], [Int])
splitHalves xs = splitAt (length xs `div` 2) xs
