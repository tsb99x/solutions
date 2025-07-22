"""
If we list all the natural numbers below 10 that are multiples of 3 or 5, we get
3, 5, 6 and 9. The sum of these multiples is 23.

Find the sum of all the multiples of 3 or 5 below 1000.
"""

from functools import reduce
from operator import add

mult_of_3 = set(range(3, 1000, 3))
mult_of_5 = set(range(5, 1000, 5))

res = reduce(add, mult_of_3 | mult_of_5)

print(res) # 233168
