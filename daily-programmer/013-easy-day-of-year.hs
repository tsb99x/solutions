{-

    https://www.reddit.com/r/dailyprogrammer/comments/pzo4w/2212012_challenge_13_easy/

    [2/21/2012] Challenge #13 [easy]

    Find the number of the year for the given date.
    For example, january 1st would be 1, and december 31st is 365.

    for extra credit, allow it to calculate leap years, as well.

-}

import System.Environment (getArgs)

main :: IO ()
main = do
    [d, m, y] <- map read <$> getArgs
    print $ dayOfYear d m y

dayOfYear :: Int -> Int -> Int -> Int
dayOfYear d m y = d + sum (take (m - 1) daysInMonth)
    where daysInMonth = [31, febDays, 31, 30, 31, 30, 31, 31, 30, 31, 30]
          febDays = if isLeapYear then 29 else 28
          isLeapYear = y `mod` 4 == 0 && (y `mod` 100 /= 0 || y `mod` 400 == 0)
