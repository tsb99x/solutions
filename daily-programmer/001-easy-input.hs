import Text.Printf

import Utils

main :: IO ()
main = getInput >>= output
    where output s = putStr s >> appendFile "tmp/log.txt" s

getInput :: IO String
getInput = formMsg <$> input "your name > "
                   <*> input "your age > "
                   <*> input "your username > "

formMsg :: String -> String -> String -> String
formMsg = printf "your name is %s, \
                 \you are %s years old, \
                 \and your username is %s\n"
