{-# LANGUAGE DeriveGeneric, DeriveAnyClass #-}

import Utils (input)
import Text.Read (readMaybe)
import Control.DeepSeq (NFData, deepseq)
import GHC.Generics (Generic)

data Credential = Credential
    { login :: String
    , password :: String
    } deriving (Eq, Read, Generic, NFData)

main :: IO ()
main = output =<< authenticate =<< readCreds
    where readCreds = map readCred . lines <$> readFile "res/passwords.txt"
          output = putStrLn . ("successfully logged in as " ++)

authenticate :: [Credential] -> IO String
authenticate creds = creds `deepseq` do
    login <- input "login > "
    pass  <- input "pass > "
    if Credential login pass `elem` creds
        then return login
        else do
            putStrLn "wrong login and password combination, try again\n"
            authenticate creds

-- UTILS

readCred :: String -> Credential
readCred s = case readMaybe s of
    Just x -> x
    Nothing -> error "passwords file is malformed"
