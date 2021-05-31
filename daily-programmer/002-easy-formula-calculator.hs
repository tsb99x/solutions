import Data.Char (toLower)
import Text.Read (readMaybe)
import Control.Monad ((<$!>))
import System.IO (hFlush, stdout)

data Var = F | M | A

main :: IO ()
main = output . calculate =<< getVars =<< getMode
    where output = putStrLn . convert

getMode :: IO Var
getMode = readMode . map toLower <$> promptMode
    where promptMode = prompt "mode [f]orce, [m]ass or [a]cceleration"

readMode :: String -> Var
readMode "f" = F
readMode "m" = M
readMode "a" = A
readMode _ = error "use 'f', 'm' or 'a' to select mode"

getVars :: Var -> IO (Var, Float, Var, Float)
getVars F = packParams M A
getVars M = packParams F A
getVars A = packParams F M

packParams :: Var -> Var -> IO (Var, Float, Var, Float)
packParams a b = do
    aVal <- getVar a
    bVal <- getVar b
    return (a, aVal, b, bVal)

getVar :: Var -> IO Float
getVar F = readFloat <$!> prompt "force"
getVar M = readFloat <$!> prompt "mass"
getVar A = readFloat <$!> prompt "acceleration"

calculate :: (Var, Float, Var, Float) -> (Var, Float)
calculate (M, m, A, a) = (F, m * a)
calculate (F, f, A, a) = (M, f / a)
calculate (F, f, M, m) = (A, f / m)

convert :: (Var, Float) -> String
convert (F, f) = "F = " ++ show f
convert (M, m) = "M = " ++ show m
convert (A, a) = "A = " ++ show a

-- UTILS

readFloat :: String -> Float
readFloat str =
    case readMaybe str of
        Just x -> x
        Nothing -> error "only float values are allowed"

prompt :: String -> IO String
prompt msg = do
    putStr $ "input " ++ msg ++ " > "
    hFlush stdout
    getLine
