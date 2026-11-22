{-# LANGUAGE OverloadedStrings #-}

module Main where

import Data.List (sort)
import Data.Text qualified as T
import Text.Read (readMaybe)

main :: IO ()
main = do
  input <- lines <$> getContents
  let sizes = mapM parseSize input
  let wrapping = sum . fmap calcRibbon <$> sizes
  putStrLn $ either id show wrapping

parseSize :: String -> Either String (Int, Int, Int)
parseSize s = convert $ fmap (readMaybe . T.unpack) $ T.splitOn "x" $ T.pack s
  where
    convert [Just l, Just w, Just h] = Right (l, w, h)
    convert _ = Left $ "Line '" ++ s ++ "' is not of proper format (NxNxN)!"

calcRibbon :: (Int, Int, Int) -> Int
calcRibbon (l, w, h) = 2 * sum smallestSides + bow
  where
    bow = l * w * h
    smallestSides = take 2 $ sort [l, w, h]