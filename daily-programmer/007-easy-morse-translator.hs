{-

    https://www.reddit.com/r/dailyprogrammer/comments/pr2xr/2152012_challenge_7_easy/

    [2/15/2012] Challenge #7 [easy]

    Write a program that can translate Morse code in the format of ...---...

    A space and a slash will be placed between words. ..- / --.-

    For bonus, add the capability of going from a string to Morse code.

    Super-bonus if your program can flash or beep the Morse.

    This is your Morse to translate:

    .... . .-.. .-.. --- / -.. .- .. .-.. -.-- / .--. .-. --- --. .-. .- -- -- . .-. / --. --- --- -.. / .-.. ..- -.-. -.- / --- -. / - .... . / -.-. .... .- .-.. .-.. . -. --. . ... / - --- -.. .- -.--

-}

{-# LANGUAGE OverloadedStrings #-}

import qualified Data.Map as Map
import Data.Text (Text)
import qualified Data.Text as T
import qualified Data.Text.IO as TIO
import Data.Tuple (swap)
import Data.Maybe (fromJust)

main :: IO ()
main = do
    TIO.putStrLn $ decode
        ".... . .-.. .-.. ---\
        \ / -.. .- .. .-.. -.--\
        \ / .--. .-. --- --. .-. .- -- -- . .-.\
        \ / --. --- --- -..\
        \ / .-.. ..- -.-. -.-\
        \ / --- -.\
        \ / - .... .\
        \ / -.-. .... .- .-.. .-.. . -. --. . ...\
        \ / - --- -.. .- -.--"
    TIO.putStrLn $ encode
        "HELLO DAILY PROGRAMMER GOOD LUCK ON THE CHALLENGES TODAY"

decode :: Text -> Text
decode msg = T.pack . unwords . map (map decodeChar . toChars) $ toWords msg
    where toWords = T.splitOn " / "
          toChars = T.splitOn " "

decodeChar :: Text -> Char
decodeChar morseChar = fromJust $ Map.lookup morseChar decoder
    where decoder = Map.fromList $ map swap morse

encode :: Text -> Text
encode = toSentence . map (toWords . map encodeChar . T.unpack) . T.words
    where toSentence = T.intercalate " / "
          toWords = T.intercalate " "

encodeChar :: Char -> Text
encodeChar char = fromJust $ Map.lookup char encoder
    where encoder = Map.fromList morse

morse :: [(Char, Text)]
morse =
    [('A', ".-")    ,('B', "-...")
    ,('C', "-.-.")  ,('D', "-..")
    ,('E', ".")     ,('F', "..-.")
    ,('G', "--.")   ,('H', "....")
    ,('I', "..")    ,('J', ".---")
    ,('K', "-.-")   ,('L', ".-..")
    ,('M', "--")    ,('N', "-.")
    ,('O', "---")   ,('P', ".--.")
    ,('Q', "--.-")  ,('R', ".-.")
    ,('S', "...")   ,('T', "-")
    ,('U', "..-")   ,('V', "...-")
    ,('W', ".--")   ,('X', "-..-")
    ,('Y', "-.--")  ,('Z', "--..")
    ,('1', ".----") ,('2', "..---")
    ,('3', "...--") ,('4', "....-")
    ,('5', ".....") ,('6', "-....")
    ,('7', "--...") ,('8', "---..")
    ,('9', "----.") ,('0', "-----")
    ]
