{-

    https://www.reddit.com/r/dailyprogrammer/comments/r59kk/3202012_challenge_28_easy/

    [3/20/2012] Challenge #28 [easy]

    The array duplicates problem is when one integer is in an array for more than once.

    If you are given an array with integers between 1 and 1,000,000 or in some other interval and one integer is in the array twice.
    How can you determine which one?

    Your task is to write code to solve the challenge.

    Note: try to find the most efficient way to solve this challenge.

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))
import Data.List (sort)

import qualified Data.Map as M

main :: IO ()
main = void . runTestTT $ test
    [ []     ~=? findDup [0, 1, 2, 3, 4, 5]
    , [5]    ~=? findDup [0, 5, 1, 2, 3, 4, 5]
    , [2, 5] ~=? findDup [2, 0, 1, 5, 3, 4, 5, 2]
    ]

findDup :: [Int] -> [Int]
findDup = values . notUniq . count
    where values = map fst
          notUniq = filter ((> 1) . snd)
          count = M.toList . M.fromListWith (+) . (`zip` repeat 1)
