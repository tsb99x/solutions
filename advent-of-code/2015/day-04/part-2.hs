module Main where

import Data.ByteString.Char8 (pack)
import Data.List (find, isPrefixOf)
import Distribution.Utils.MD5 (md5)

main :: IO ()
main = do
  putStrLn . maybe "No solutions exists!" show $ solve "ckczppom"

solve :: String -> Maybe Int
solve a =
  find isSolution [0 ..]
  where
    isSolution = isPrefixOf "000000" . findMd5 . (a ++) . show
    findMd5 = show . md5 . pack