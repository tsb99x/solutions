{-

    https://www.reddit.com/r/dailyprogrammer/comments/r0r3h/3172012_challenge_27_easy/

    [3/17/2012] Challenge #27 [easy]

    Write a program that accepts a year as input and outputs the century the year belongs in (e.g. 18th century's year ranges are 1701 to 1800) and whether or not the year is a leap year.
    Pseudocode for leap year can be found [here](http://en.wikipedia.org/wiki/Leap_year#Algorithm).

    Sample run:

    Enter Year: 1996

    Century: 20

    Leap Year: Yes

    Enter Year: 1900

    Century: 19

    Leap Year: No

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))

data LeapYear = Common | Leap
    deriving (Eq, Show)

main :: IO ()
main = void . runTestTT $ test
    [ (20, Leap)   ~=? centuryAndLeapYear 1996
    , (19, Common) ~=? centuryAndLeapYear 1900
    ]

centuryAndLeapYear :: Int -> (Int, LeapYear)
centuryAndLeapYear y = (century, isLeapYear y)
    where century = fullCent + signum yearsInNextCent
          (fullCent, yearsInNextCent) = divMod y 100

isLeapYear :: Int -> LeapYear
isLeapYear y = if y `mod` 4 == 0 && (y `mod` 100 /= 0 || y `mod` 400 == 0)
    then Leap
    else Common
