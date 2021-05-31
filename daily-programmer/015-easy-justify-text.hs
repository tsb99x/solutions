{-

    https://www.reddit.com/r/dailyprogrammer/comments/q4c34/2242012_challenge_15_easy/

    [2/24/2012] Challenge #15 [easy]

    Write a program to left or right justify a text file

-}

import Text.Read (readMaybe)
import System.Environment (getArgs)

data JustifyMode
    = JustifyLeft
    | JustifyRight Int

-- Launch this solution like this:
--    cat 015-easy-justify-text.hs | runghc 015-easy-justify-text.hs --right 80

main :: IO ()
main = do
    mode <- parseArgs <$> getArgs
    strs <- trim . lines <$> getContents
    putStr . unlines . pad mode (maxLen strs) $ strs
    where maxLen = maximum . map length
          trim = map (trimLeft . trimRight)
          trimLeft = dropWhile (== ' ')
          trimRight = foldr skipSpaceOnRight []

pad :: JustifyMode -> Int -> [String] -> [String]
pad JustifyLeft _ strs = strs
pad (JustifyRight n) maxLen strs = map padRight strs
    where padRight x = replicate (padSize x) ' ' ++ x
          padSize x = ruler - length x
          ruler = max maxLen n

parseArgs :: [String] -> JustifyMode
parseArgs ["--right", n] = JustifyRight $ readInt mustBePositive n
parseArgs ["--left"] = JustifyLeft
parseArgs _ = error "unknown arguments or their combination passed"

skipSpaceOnRight :: Char -> String -> String
skipSpaceOnRight ' ' [] = []
skipSpaceOnRight x acc = x:acc

-- UTILS

mustBePositive :: Int -> Int
mustBePositive val
    | val <= 0 = error "only positive integers allowed"
    | otherwise = val

readInt :: (Int -> Int) -> String -> Int
readInt validator str =
    case readMaybe str of
        Just x -> validator x
        Nothing -> error "only int values are allowed"
