name = input('your name > ')
age = input('your age > ')
username = input('your username > ')

output = f'your name is {name}, you are {age} years old, and your username is {username}'

print(output)

with open('tmp/log.txt', 'a') as log:
    log.write(f'{output}\n')
