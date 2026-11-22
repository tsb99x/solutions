module Main where

import Data.Functor ((<&>))
import Data.List (findIndex)

main :: IO ()
main = do
  input <- getLine
  let floors = scanl (+) 0 $ eval <$> input
  let basementIndex = findIndex (< 0) floors
  putStrLn $ maybe "Santa hasn't entered the basement!" show basementIndex

main' :: IO ()
main' =
  getLine
    <&> (eval <$>)
    <&> scanl (+) 0
    <&> findIndex (< 0)
    <&> maybe "Santa hasn't entered the basement!" show
    >>= print

eval :: Char -> Int
eval '(' = 1
eval ')' = -1
eval _ = 0
