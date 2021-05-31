{-

    https://www.reddit.com/r/dailyprogrammer/comments/pp53w/2142012_challenge_6_easy/

    [2/14/2012] Challenge #6 [easy]

    You're challenge for today is to create a program that can calculate pi accurately to at least 30 decimal places.

    Try not to cheat :)

-}

-- This is an implementation of the Pi Spigot Algorithm

import Data.List (unfoldr)

main :: IO ()
main = putStrLn $ piDigits 30

piDigits :: Int -> String
piDigits n = putComma . convert . feedDigits $ initialize len
    where len = (10 * n) `div` 3
          initialize = flip replicate 2
          feedDigits = take n . unfoldr (Just . digit)
          convert = concatMap show
          putComma = ("3," ++) . tail

digit :: [Int] -> (Int, [Int])
digit = weave . index . multiply
    where multiply = map (*10)
          index = zip [0..]
          weave = foldr calc (0, [])

calc :: (Int, Int) -> (Int, [Int]) -> (Int, [Int])
calc (i, x) (carry, rems) = (quo, rem:rems)
    where val = x + carry * (i + 1)
          den = if i == 0 then 10 else (2 * i) + 1
          (quo, rem) = val `divMod` den
