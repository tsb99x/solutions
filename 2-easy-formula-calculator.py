"""
[easy] challenge #2

Hello, coders!
An important part of programming is being able to apply your programs,
so your challenge for today is to create a calculator application that has use in your life.
It might be an interest calculator, or it might be something that you can use in the classroom.
For example, if you were in physics class, you might want to make a F = M * A calc.

EXTRA CREDIT: make the calculator have multiple functions!
Not only should it be able to calculate F = M * A, but also A = F/M, and M = F/A!
"""
from typing import Optional

mode = input('choose calc mode [f] = [m] * [a] > ')
params = {'f', 'm', 'a'}

if mode not in params:
    raise RuntimeError('only [f], [m], or [a] modes are allowed!')


def input_param(param: str) -> Optional[float]:
    if mode in params - {param}:
        return float(input(f'value of [{param}] > '))


f = input_param('f')
m = input_param('m')
a = input_param('a')

if mode == 'f':
    print(f'[f] = {m * a}')

if mode == 'm':
    print(f'[m] = {f / a}')

if mode == 'a':
    print(f'[a] = {f / m}')
