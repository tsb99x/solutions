{-# LANGUAGE OverloadedStrings #-}

module Main where

import Data.Function ((&))
import Data.Functor ((<&>))
import Data.List (sort)
import Data.Text qualified as T
import Text.Parsec (parse)
import Text.Read (readMaybe)

main :: IO ()
main = do
  input <- lines <$> getContents
  let sizes = mapM parseSize input
  let wrapping = sum . fmap calcWrapping <$> sizes
  putStrLn $ either id show wrapping

parseSize :: String -> Either String (Int, Int, Int)
parseSize s = convert $ fmap (readMaybe . T.unpack) $ T.splitOn "x" $ T.pack s
  where
    convert [Just l, Just w, Just h] = Right (l, w, h)
    convert _ = Left $ "Line '" ++ s ++ "' is not of proper format (NxNxN)!"

calcWrapping :: (Int, Int, Int) -> Int
calcWrapping (l, w, h) = 2 * l * w + 2 * w * h + 2 * h * l + slack
  where
    slack = product smallestSide
    smallestSide = take 2 $ sort [l, w, h]

main' :: IO ()
main' =
  getContents
    <&> lines
    <&> mapM parseSize'
    <&> (sum . fmap calcWrapping <$>)
    <&> either id show
    >>= putStrLn

parseSize' :: String -> Either String (Int, Int, Int)
parseSize' s =
  T.unpack <$> T.splitOn "x" (T.pack s)
    & mapM readMaybe
    & convert
  where
    convert (Just [l, w, h]) = Right (l, w, h)
    convert _ = Left $ "Line '" ++ s ++ "' is not of proper format (NxNxN)!"

calcWrapping' :: (Int, Int, Int) -> Int
calcWrapping' (l, w, h) = 2 * l * w + 2 * w * h + 2 * h * l + slack
  where
    slack = product smallestSide
    smallestSide = take 2 $ sort [l, w, h]
