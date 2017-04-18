from __future__ import division
from os import walk
import csv

# Read lines from data file.
def import_text(filename, separator):
    for line in csv.reader(open(filename), delimiter=separator,
                           skipinitialspace=True):
        if line:
            yield line
mypath = 'D:\Sharp\Images\Street art\ArtsyLinks'

f = []
for (dirpath, dirnames, filenames) in walk(mypath):
    f.extend(filenames)
    break

pathToLinksFile = 'D:\Sharp\Images\Street art\A\\Links.csv'

for file in f:
    for data in import_text(mypath+"\\"+file, ','):
        if "artwork" in data[0] and "Contact gallery" in data[1]:
            with open(pathToLinksFile, "a") as myfile:
                myfile.write(data[0]+"\n")
