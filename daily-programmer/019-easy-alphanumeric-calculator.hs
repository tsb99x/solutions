{-

    https://www.reddit.com/r/dailyprogrammer/comments/qlwrc/372012_challenge_19_easy/

    [3/7/2012] Challenge #19 [easy]

    Challenge #19 will use [The Adventures of Sherlock Holmes](http://www.gutenberg.org/cache/epub/1661/pg1661.txt) from [Project Gutenberg](http://www.gutenberg.org/).

    Write a program that counts the number of alphanumeric characters there are in The Adventures of Sherlock Holmes.
    Exclude the Project Gutenberg header and footer, book title, story titles, and chapters.
    Post your code and the alphanumeric character count.

-}

import Data.Char (isDigit, isAlpha, toLower)

import qualified Data.Map as M

main :: IO ()
main = stats . charsCount . alphaNum . stripInfo =<< readFile "res/pg1661.txt"
    where stripInfo = concat . dropFooter . dropHeader . splitByLines
          dropHeader = init . takeWhile (/= footerStart)
          dropFooter = tail . dropWhile (/= headerEnd)
          splitByLines = lines . filter (/= '\r')
          alphaNum = filter (\c -> isAlpha c || isDigit c) . map toLower
          charsCount = M.toList . M.fromListWith (+) . flip zip (repeat 1)
          stats = mapM_ (\(k, v) -> putStrLn (show k ++ " = " ++ show v))
          headerEnd = "*** START OF THIS PROJECT GUTENBERG EBOOK THE ADVENTURES OF SHERLOCK HOLMES ***"
          footerStart = "*** END OF THIS PROJECT GUTENBERG EBOOK THE ADVENTURES OF SHERLOCK HOLMES ***"
