{-

    https://www.reddit.com/r/dailyprogrammer/comments/qheeu/342012_challenge_17_easy/

    [3/4/2012] Challenge #17 [easy]

    [deleted]

-}

-- Apparently, this challenge was about printing a triangle of stars to a terminal.

main :: IO ()
main = mapM_ putStrLn $ triangle 10
    where triangle n = map (`replicate` '*') [1..n]
