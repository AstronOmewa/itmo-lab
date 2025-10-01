import os, subprocess
import datetime, time, math
os.chdir(os.path.dirname(os.path.abspath(__file__)))

path = input('path: ')
main_class = input('main-class: ')
name = input('bundle name: ')
username = input("sXXXXXX name: ")

if not name:
    name = f'bundle-{math.floor(time.time())}'

if not path: 
    path = os.path.dirname(os.path.abspath(__file__))
    path = '/'.join(path.split('\\'))+'/'

print('/'.join(path.split('\\')))
classes_string = ''
# subprocess.run(['cd',f'{])
for (dir, _, files) in os.walk(path):
    
    for file in files:
        filename, ext = ''.join(file.split('.')[:-1]), file.split('.')[-1]
        
        if ext == 'java':
            classes_string += filename+'.class '
            subprocess.run(['javac', file])

with open('MANIFEST.mf','w') as f:
    f.write(f'Main-Class: {main_class}\n')
with open(classes_string.split()[0]):
    subprocess.run(['jar','-cfm',f'{name}.jar', f'MANIFEST.mf',f'{classes_string.strip()}']) 
    subprocess.run(['java', '-jar', f'{name}.jar' ])
    subprocess.run(f'scp -P 2222 {path}{name}.jar {username}@se.ifmo.ru:~/'.split(' ')) 

print(classes_string)