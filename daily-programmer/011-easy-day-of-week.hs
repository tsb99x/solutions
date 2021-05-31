{-

    https://www.reddit.com/r/dailyprogrammer/comments/pwons/2192012_challenge_11_easy/

    [2/19/2012] Challenge #11 [easy]

    The program should take three arguments.
    The first will be a day, the second will be month, and the third will be year.
    Then, your program should compute the day of the week that date will fall on.

-}

import System.Environment (getArgs)

main :: IO ()
main = do
    [d, m, y] <- map read <$> getArgs
    print $ toStr $ dayOfWeek d m y

{- This is an adaptation of the Sakamoto's Algorithm -}

toStr :: Int -> String
toStr 0 = "Sunday"
toStr 1 = "Monday"
toStr 2 = "Tuesday"
toStr 3 = "Wednesday"
toStr 4 = "Thursday"
toStr 5 = "Friday"
toStr 6 = "Saturday"

dayOfWeek :: Int -> Int -> Int -> Int
dayOfWeek day month year =
    (y + y `div` 4 - y `div` 100 + y `div` 400 + t month + day) `mod` 7
    where y = if month < 3 then year - 1 else year

t :: Int -> Int
t  1 = 0
t  2 = 3
t  3 = 2
t  4 = 5
t  5 = 0
t  6 = 3
t  7 = 5
t  8 = 1
t  9 = 4
t 10 = 6
t 11 = 2
t 12 = 4
