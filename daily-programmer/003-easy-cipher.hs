import System.IO (hFlush, stdout)
import Control.Monad ((<$!>))
import Text.Read (readMaybe)
import Data.Maybe (fromMaybe)

import qualified Data.Map.Strict as Map

main :: IO ()
main = output . process =<< input
    where output str = putStrLn $ "result: " ++ str

data Mode = Encrypt | Decrypt

input :: IO (Mode, String, Int)
input = do
    mode <- readMode <$!> prompt "[e]ncrypting or [d]ecrypting mode"
    msg <- prompt "message"
    rot <- readInt <$!> prompt "rotation shift"
    return (mode, msg, rot)

readMode :: String -> Mode
readMode "e" = Encrypt
readMode "d" = Decrypt
readMode _   = error "only [e] or [d] symbols are allowed"

process :: (Mode, String, Int) -> String
process (mode, msg, shift) = map convert msg
    where convert chr = fromMaybe chr $ Map.lookup chr charMapping
          charMapping = mapping mode shift

mapping :: Mode -> Int -> Map.Map Char Char
mapping mode shift =
    Map.fromList $ case mode of
        Encrypt -> zip original derived
        Decrypt -> zip derived original
    where derived = ys ++ xs
          (xs, ys) = splitAt realShift original
          realShift = shift `mod` length original
          original = ['a'..'z'] ++ ['A'..'Z']

-- UTILS

readInt :: String -> Int
readInt str =
    case readMaybe str of
        Just x -> x
        Nothing -> error "only int values are allowed"

prompt :: String -> IO String
prompt msg = do
    putStr $ "input " ++ msg ++ " > "
    hFlush stdout
    getLine
