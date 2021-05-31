{-

    https://www.reddit.com/r/dailyprogrammer/comments/qnkro/382012_challenge_20_easy/

    [3/8/2012] Challenge #20 [easy]

    create a program that will find all prime numbers below 2000

-}

-- Sieve of Eratosthenes Method

import Data.List (foldl')

main :: IO ()
main = print . reverse . foldl' sievePrimes [] $ [2..n]
    where n = 2000

sievePrimes :: [Int] -> Int -> [Int]
sievePrimes acc x
    | notElem 0 $ map (mod x) acc = x:acc
    | otherwise = acc
