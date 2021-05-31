{-

    https://www.reddit.com/r/dailyprogrammer/comments/pu1rf/2172012_challenge_9_easy/

    [2/17/2012] Challenge #9 [easy]

    write a program that will allow the user to input digits, and arrange them in numerical order.

    for extra credit, have it also arrange strings in alphabetical order

-}

import System.Environment (getArgs)
import Data.List (sort)

main :: IO ()
main =  mapM_ putStrLn . sort =<< getArgs
