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
