{-

    https://www.reddit.com/r/dailyprogrammer/comments/qit0h/352012_challenge_18_easy/

    [3/5/2012] Challenge #18 [easy]

    Often times in commercials, phone numbers contain letters so that they're easy to remember (e.g. 1-800-VERIZON).
    Write a program that will convert a phone number that contains letters into a phone number with only numbers and the appropriate dash.
    Click [here](https://en.wikipedia.org/wiki/Telephone_keypad) to learn more about the telephone keypad.

    Example Execution: Input: 1-800-COMCAST Output: 1-800-266-2278

-}

{-# LANGUAGE TupleSections #-}

import Test.HUnit ((~=?), runTestTT, Testable(test))
import Control.Monad (void)
import Data.Maybe (fromMaybe)
import Data.Char (intToDigit)
import Data.List (intercalate)

import qualified Data.Map as M

main :: IO ()
main = void . runTestTT $ test
    [ "1-800-266-2278" ~=? convert "1-800-COMCAST"
    , "1-800-837-4966" ~=? convert "1-800-VERIZON"
    ]

convert :: String -> String
convert = insertDash . map toDigit
    where toDigit c = fromMaybe c $ M.lookup c letterToDigit
          insertDash = intercalate "-" . toList . splitAt 9
          toList (x, y) = [x, y]

letterToDigit :: M.Map Char Char
letterToDigit = M.fromList $ concatMap expandTuple
    [ ("ABC",  2)
    , ("DEF",  3)
    , ("GHI",  4)
    , ("JKL",  5)
    , ("MNO",  6)
    , ("PQRS", 7)
    , ("TUV",  8)
    , ("WXYZ", 9)
    ]
    where expandTuple (ks, v) = map (, intToDigit v) ks
