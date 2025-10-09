import os, subprocess
import datetime, time, math
os.chdir(os.path.dirname(os.path.abspath(__file__)))

path = input('path: ')
name = input('bundle name: ')

if not name:
    name = f'bundle-{math.floor(time.time())}'

if not path: 
    path = os.path.dirname(os.path.abspath(__file__))
    path = '/'.join(path.split('\\'))+'/'

path='/'.join(path.split('\\'))
classes_string = ''

for (dir, _, files) in os.walk(path):
    print(dir)
    for file in files:
        filename, ext = ''.join(file.split('.')[:-1]), file.split('.')[-1]
        
        if ext == 'java':
            classes_string += filename+'.class '
            subprocess.run(['javac', f'{path}{file}'])

print(classes_string)