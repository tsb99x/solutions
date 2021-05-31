import System.IO (hFlush, stdout)
import Text.Read (readMaybe)
import System.Random (getStdGen, randomRs, StdGen)
import Data.List (unfoldr)
import Control.Monad ((<$!>))

main :: IO ()
main = output . generate =<< input
    where output = mapM_ putStrLn

input :: IO (StdGen, Int, Int)
input = do
    gen <- getStdGen
    count <- readInt mustBePositive <$!> prompt "passwords count"
    length <- readInt mustBePositive <$!> prompt "length of a password"
    return (gen, count, length)

generate :: (StdGen, Int, Int) -> [String]
generate (gen, count, len) = takePasses gen count len

takePasses :: StdGen -> Int -> Int -> [String]
takePasses gen count len = take count $ chunk len chars
    where chunk len = unfoldr $ Just . splitAt len
          chars = map (alphabet !!) sequence
          sequence = randomRs (0, length alphabet - 1) gen
          alphabet = alpha ++ numeric ++ symbols
          alpha = ['a'..'z'] ++ ['A'..'Z']
          numeric = ['0'..'9']
          symbols = ['_', '@', '-', '+', '=', '$', '#']

-- UTILS

readInt :: (Int -> Int) -> String -> Int
readInt validator str =
    case readMaybe str of
        Just x -> validator x
        Nothing -> error "only int values are allowed"

mustBePositive :: Int -> Int
mustBePositive val
    | val <= 0 = error "only positive integers allowed"
    | otherwise = val

prompt :: String -> IO String
prompt msg = do
    putStr $ "input " ++ msg ++ " > "
    hFlush stdout
    getLine
