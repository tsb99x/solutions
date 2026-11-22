module Main where

import Data.List (nub)

type Pos = (Int, Int)

type Move = (Int, Int)

main :: IO ()
main = do
  input <- getLine
  let movements = movement <$> input
  let houses = scanl sumCoords (0, 0) movements
  print . length $ nub houses

movement :: Char -> Move
movement '>' = (1, 0)
movement '<' = (-1, 0)
movement '^' = (0, -1)
movement 'v' = (0, 1)
movement _ = (0, 0)

sumCoords :: Pos -> Move -> Pos
sumCoords (a, b) (c, d) = (a + c, b + d)