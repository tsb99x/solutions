{-

    https://www.reddit.com/r/dailyprogrammer/comments/pserp/2162012_challenge_8_easy/

    [2/16/2012] Challenge #8 [easy]

    write a program that will print the song "99 bottles of beer on the wall".

    for extra credit, do not allow the program to print each loop on a new line.

-}

main :: IO ()
main = putStr $ concatMap verse [99,98..0]

verse :: Int -> String
verse 0 =
    "No more bottles of beer on the wall, no more bottles of beer.\n\
    \We've taken them down and passed them around; \
    \now we're drunk and passed out!\n"

verse curr =
    concat
        [ form curr, " of beer on the wall, "
        , form curr, " of beer.\nTake one down, pass it around, "
        , form next, " of beer on the wall...\n"
        ]
    where next = curr - 1

form :: Int -> String
form 0 = "no bottles"
form 1 = "1 bottle"
form x = show x ++ " bottles"
