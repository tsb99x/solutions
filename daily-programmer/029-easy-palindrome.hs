{-

    https://www.reddit.com/r/dailyprogrammer/comments/r8a70/3222012_challenge_29_easy/

    [3/22/2012] Challenge #29 [easy]

    A [Palindrome](http://en.wikipedia.com/wiki/Palindrome) is a sequence that is the same in reverse as it is forward.

    I.e. hannah, 12321.

    Your task is to write a function to determine whether a given string is palindromic or not.

    Bonus: Support multiple lines in your function to validate Demetri Martin's [224 word palindrome poem](http://www.pastemagazine.com/articles/2009/02/demetri-martins-palindrome-poem.html).

    Thanks to _lerp for submitting this idea in [r/dailyprogrammer_ideas](https://www.reddit.com/r/dailyprogrammer_ideas/)!

-}

import Control.Monad (void)
import Test.HUnit ((~=?), runTestTT, Testable(test))

main :: IO ()
main = void . runTestTT $ test
    [ False ~=? isPalindrome "tratata"
    , True  ~=? isPalindrome "ABBA"
    , True  ~=? isPalindrome "02022020"
    ]

isPalindrome :: String -> Bool
isPalindrome = (==) <*> reverse
