from __future__ import division

import csv
import random
import numpy as np

from sklearn import linear_model
from sklearn import preprocessing
from sklearn.ensemble import IsolationForest
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import f_classif
from sklearn.metrics import mean_absolute_error
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import cross_val_predict
from sklearn.model_selection import train_test_split
from sklearn.svm import SVR


# Read lines from data file.
def import_text(filename, separator):
    for line in csv.reader(open(filename), delimiter=separator,
                           skipinitialspace=True):
        if line:
            yield line


# Shuffle data
def shuffleData(list1, list2):
    list1_shuf = []
    list2_shuf = []
    index_shuf = range(len(list1))
    random.shuffle(index_shuf)
    for i in index_shuf:
        list1_shuf.append(list1[i])
        list2_shuf.append(list2[i])
    return list1_shuf, list2_shuf


pathToClassemesFile = 'D:\Sharp\Images\Classemes\Classemes.csv'
pathToClassemesNewFile = 'D:\Sharp\Images\Classemes\ClassemesNew.csv'
pathToClassemesNewStylesFile = 'D:\Sharp\Images\Classemes\ClassemesNewStyles.csv'

allSamplesFeatures = [[0 for z in range(2659)] for j in range(22634)]
allSamplesDigits = [0 for z in range(22634)]
i = 0
for data in import_text(pathToClassemesFile, ';'):
    allSamplesFeatures[i] = [ float(x) for x in data[0:2658] ]
    allSamplesDigits[i] = data[2659]
    i += 1

X_train, X_test, y_train, y_test = train_test_split(allSamplesFeatures, allSamplesDigits, test_size=0.3, random_state=0)
# Remove outliers: http://scikit-learn.org/stable/modules/outlier_detection.html
# http://scikit-learn.org/stable/modules/sgd.html
clf = IsolationForest()
clf.fit(X_train)
y_pred_train = clf.predict(X_train).tolist()

i = 0
while i < len(y_pred_train):
    if y_pred_train[i] == -1:
        del X_train[i]
        del y_train[i]
        del y_pred_train[i]
    else:
        i += 1

# Feature selection
# http://scikit-learn.org/stable/modules/generated/sklearn.feature_selection.f_regression.html#sklearn.feature_selection.f_regression
model = SelectKBest(f_classif, k=512)
X_train = model.fit_transform(X_train, y_train).tolist()

print X_train

with open(pathToClassemesNewFile, "wb") as f:
    writer = csv.writer(f)
    writer.writerows(X_train)

with open(pathToClassemesNewStylesFile, "wb") as f:
    writer = csv.writer(f)
    writer.writerows(zip(y_train))


pathToPiCoDesFile = 'D:\Sharp\Images\PiCoDes\PiCoDes.csv'
pathToPiCoDesNewFile = 'D:\Sharp\Images\PiCoDes\PiCoDesNew.csv'
pathToPiCoDesNewStylesFile = 'D:\Sharp\Images\PiCoDes\PiCoDesNewStyles.csv'

allSamplesFeatures = [[0 for z in range(2048)] for j in range(22634)]
allSamplesDigits = [0 for z in range(22634)]
i = 0
for data in import_text(pathToPiCoDesFile, ';'):
    allSamplesFeatures[i] = [ float(x) for x in data[0:2047] ]
    allSamplesDigits[i] = data[2048]
    i += 1

X_train, X_test, y_train, y_test = train_test_split(allSamplesFeatures, allSamplesDigits, test_size=0.3, random_state=0)
# Remove outliers: http://scikit-learn.org/stable/modules/outlier_detection.html
# http://scikit-learn.org/stable/modules/sgd.html
clf = IsolationForest()
clf.fit(X_train)
y_pred_train = clf.predict(X_train).tolist()

i = 0
while i < len(y_pred_train):
    if y_pred_train[i] == -1:
        del X_train[i]
        del y_train[i]
        del y_pred_train[i]
    else:
        i += 1

# Feature selection
# http://scikit-learn.org/stable/modules/generated/sklearn.feature_selection.f_regression.html#sklearn.feature_selection.f_regression
model = SelectKBest(f_classif, k=512)
X_train = model.fit_transform(X_train, y_train).tolist()

with open(pathToPiCoDesNewFile, "wb") as f:
    writer = csv.writer(f)
    writer.writerows(X_train)

with open(pathToPiCoDesNewStylesFile, "wb") as f:
    writer = csv.writer(f)
    writer.writerows(zip(y_train))