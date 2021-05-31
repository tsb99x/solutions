{-

    https://www.reddit.com/r/dailyprogrammer/comments/qxuug/3152012_challenge_25_easy/

    [3/15/2012] Challenge #25 [easy]

    In an election, the person with the majority of the votes is the winner.
    Sometimes due to similar number of votes, there are no winners.

    Your challenge is to write a program that determines the winner of a vote, or shows that there are no winners due to a lack of majority.

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))

type Participant = (String, Int)

main :: IO ()
main = void . runTestTT $ test
    [ Nothing       ~=? findWinner []
    , Just ("A", 1) ~=? findWinner [("A", 1)]
    , Nothing       ~=? findWinner [("A", 2), ("B", 4), ("C", 2)]
    , Just ("B", 5) ~=? findWinner [("A", 2), ("B", 5), ("C", 2)]
    ]

findWinner :: [Participant] -> Maybe Participant
findWinner ps = headMaybe $ filter moreThanMajority ps
    where moreThanMajority = (>= majority votes) . snd
          majority = (+ 1) . (`div` 2)
          votes = sum . map snd $ ps

headMaybe :: [a] -> Maybe a
headMaybe (x:_) = Just x
headMaybe []    = Nothing
