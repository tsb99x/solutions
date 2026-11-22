module Main where

import Control.Arrow ((>>>))
import Data.Functor ((<&>))

main :: IO ()
main = do
  input <- getLine
  print . sum $ eval <$> input

main' :: IO ()
main' = getLine <&> (sum . (eval <$>)) >>= print

main'' :: IO ()
main'' =
  getLine
    <&> (eval <$>)
    <&> sum
    >>= print

-- main =
--   getLine
--     <&> ((eval <$>) >>>) sum
--     >>= print

eval :: Char -> Int
eval '(' = 1
eval ')' = -1
eval _ = 0
