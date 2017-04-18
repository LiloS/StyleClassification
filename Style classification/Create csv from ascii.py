from __future__ import division
from os import walk
import csv


# Read lines from data file.
def import_text(filename, separator):
    for line in csv.reader(open(filename), delimiter=separator,
                           skipinitialspace=True):
        if line:
            yield line


def ascii_to_csv(pathToAscii, pathToFile, styleName):
    f = []
    for (dirpath, dirnames, filenames) in walk(mypath):
        f.extend(filenames)
        break

    for file in f:
        fileOpen = open(mypath+"\\"+file, 'r')
        for line in fileOpen:
            if line:
                with open(pathToClassemesFile, "a") as myfile:
                    myfile.write(line.rstrip()+";")
        with open(pathToClassemesFile, "a") as myfile:
            myfile.write(styleName+"\n")

#--------------------------------Classemes-----------------------------
mypath = 'D:\Sharp\Images\AbstractArt\FeaturesClassemes'
pathToClassemesFile = 'D:\Sharp\Images\AbstractArt\Classemes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "AbstractArt")

mypath = 'D:\Sharp\Images\Figurative\FeaturesClassemes'
pathToClassemesFile = 'D:\Sharp\Images\Figurative\Classemes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Figurative")

mypath = 'D:\Sharp\Images\Hyperrealism\FeaturesClassemes'
pathToClassemesFile = 'D:\Sharp\Images\Hyperrealism\Classemes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Hyperrealism")

mypath = 'D:\Sharp\Images\Pop\FeaturesClassemes'
pathToClassemesFile = 'D:\Sharp\Images\Pop\Classemes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Pop")

mypath = 'D:\Sharp\Images\StreetArt\FeaturesClassemes'
pathToClassemesFile = 'D:\Sharp\Images\StreetArt\Classemes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "StreetArt")

#--------------------------------PiCoDes-----------------------------
mypath = 'D:\Sharp\Images\AbstractArt\FeaturesPicodes'
pathToClassemesFile = 'D:\Sharp\Images\AbstractArt\Picodes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "AbstractArt")

mypath = 'D:\Sharp\Images\Figurative\FeaturesPicodes'
pathToClassemesFile = 'D:\Sharp\Images\Figurative\Picodes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Figurative")

mypath = 'D:\Sharp\Images\Hyperrealism\FeaturesPicodes'
pathToClassemesFile = 'D:\Sharp\Images\Hyperrealism\Picodes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Hyperrealism")

mypath = 'D:\Sharp\Images\Pop\FeaturesPicodes'
pathToClassemesFile = 'D:\Sharp\Images\Pop\Picodes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Pop")

mypath = 'D:\Sharp\Images\StreetArt\FeaturesPicodes'
pathToClassemesFile = 'D:\Sharp\Images\StreetArt\Picodes.csv'

ascii_to_csv(mypath, pathToClassemesFile, "StreetArt")

#--------------------------------Low level-----------------------------
mypath = 'D:\Sharp\Images\AbstractArt\FeaturesLow'
pathToClassemesFile = 'D:\Sharp\Images\AbstractArt\Low.csv'

ascii_to_csv(mypath, pathToClassemesFile, "AbstractArt")

mypath = 'D:\Sharp\Images\Figurative\FeaturesLow'
pathToClassemesFile = 'D:\Sharp\Images\Figurative\Low.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Figurative")

mypath = 'D:\Sharp\Images\Hyperrealism\FeaturesLow'
pathToClassemesFile = 'D:\Sharp\Images\Hyperrealism\Low.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Hyperrealism")

mypath = 'D:\Sharp\Images\Pop\FeaturesLow'
pathToClassemesFile = 'D:\Sharp\Images\Pop\Low.csv'

ascii_to_csv(mypath, pathToClassemesFile, "Pop")

mypath = 'D:\Sharp\Images\StreetArt\FeaturesLow'
pathToClassemesFile = 'D:\Sharp\Images\StreetArt\Low.csv'

ascii_to_csv(mypath, pathToClassemesFile, "StreetArt")