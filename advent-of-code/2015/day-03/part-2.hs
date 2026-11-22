module Main where

import Data.List (nub)

type Pos = (Int, Int)

type Move = (Int, Int)

main :: IO ()
main = do
  input <- getLine
  let movements = movement <$> input
  let santaHouses = process $ everyOther movements
  let roboHouses = process . everyOther $ drop 1 movements
  print . length $ nub (santaHouses ++ roboHouses)
  where
    process = scanl sumCoords (0, 0)

everyOther :: [a] -> [a]
everyOther (x : y : xs) = x : everyOther xs
everyOther _ = []

movement :: Char -> Move
movement '>' = (1, 0)
movement '<' = (-1, 0)
movement '^' = (0, -1)
movement 'v' = (0, 1)
movement _ = (0, 0)

sumCoords :: Pos -> Move -> Pos
sumCoords (a, b) (c, d) = (a + c, b + d)