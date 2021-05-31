{-

    https://www.reddit.com/r/dailyprogrammer/comments/pv98f/2182012_challenge_10_easy/

    [2/18/2012] Challenge #10 [easy]

    The exercise today asks you to validate a telephone number, as if written on an input form.
    Telephone numbers can be written as ten digits, or with dashes, spaces, or dots between the three segments, or with the area code parenthesized; both the area code and any white space between segments are optional.

    Thus, all of the following are valid telephone numbers: 1234567890, 123-456-7890, 123.456.7890, (123)456-7890, (123) 456-7890 (note the white space following the area code), and 456-7890.

    The following are not valid telephone numbers: 123-45-6789, 123:4567890, and 123/456-7890.

    source: programmingpraxis.com

-}

import System.Environment (getArgs)
import Data.Char (isDigit)

main :: IO ()
main = mapM_ (putStrLn . convert . process) =<< getArgs
    where process number = (number, validate number)
          convert (number, result) = number ++ " : " ++ show result

validate :: String -> Bool
validate number
    | masked == "#######"        = True
    | masked == "### ####"       = True
    | masked == "###-####"       = True
    | masked == "###.####"       = True
    | masked == "##########"     = True
    | masked == "### ### ####"   = True
    | masked == "###-###-####"   = True
    | masked == "###.###.####"   = True
    | masked == "(###)#######"   = True
    | masked == "(###)### ####"  = True
    | masked == "(###)###-####"  = True
    | masked == "(###)###.####"  = True
    | masked == "(###) #######"  = True
    | masked == "(###) ### ####" = True
    | masked == "(###) ###-####" = True
    | masked == "(###) ###.####" = True
    | otherwise                  = False
    where masked = map mask number
          mask c = if isDigit c then '#' else c
