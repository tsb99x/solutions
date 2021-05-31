{-

    https://www.reddit.com/r/dailyprogrammer/comments/qzil1/3162012_challenge_26_easy/

    [3/16/2012] Challenge #26 [easy]

    you have a string "ddaaiillyypprrooggrraammeerr".
    We want to remove all the consecutive duplicates and put them in a separate string, which yields two separate instances of the string "dailyprogramer".

    use this list for testing:

    input: "balloons"

    expected output: "balons" "lo"

    input: "ddaaiillyypprrooggrraammeerr"

    expected output: "dailyprogramer" "dailyprogramer"

    input: "aabbccddeded"

    expected output: "abcdeded" "abcd"

    input: "flabby aapples"

    expected output: "flaby aples" "bap"

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))

main :: IO ()
main = void . runTestTT $ test
    [ ("balons", "lo")                     ~=? extractConsDup "balloons"
    , ("dailyprogramer", "dailyprogramer") ~=? extractConsDup "ddaaiillyypprrooggrraammeerr"
    , ("abcdeded", "abcd")                 ~=? extractConsDup "aabbccddeded"
    , ("flaby aples", "bap")               ~=? extractConsDup "flabby aapples"
    ]

extractConsDup :: String -> (String, String)
extractConsDup = foldr separateDup ("", "")

separateDup :: Char -> (String, String) -> (String, String)
separateDup c (x:xs, ys) | c == x = (x:xs, c:ys)
separateDup c (  xs, ys)          = (c:xs,   ys)
