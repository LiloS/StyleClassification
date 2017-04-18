from __future__ import division
import itertools
import numpy as np
import matplotlib.pyplot as plt

from sklearn import svm, datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix
import csv
from sklearn import svm
import random
from sklearn.ensemble import IsolationForest
from sklearn.feature_selection import SelectFromModel
from sklearn.svm import LinearSVC
from sklearn import linear_model
from sklearn.metrics import classification_report
from sklearn.model_selection import GridSearchCV
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix
from sklearn.metrics import confusion_matrix
from sklearn.neural_network import MLPClassifier

from sklearn.naive_bayes import GaussianNB
import csv
import random
import time

from sklearn.ensemble import GradientBoostingClassifier
from sklearn.ensemble import IsolationForest
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import f_classif
from sklearn.metrics import classification_report
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import train_test_split


def plot_confusion_matrix(cm, classes,
                          normalize=False,
                          title='Confusion matrix',
                          cmap=plt.cm.Blues):
    """
    This function prints and plots the confusion matrix.
    Normalization can be applied by setting `normalize=True`.
    """
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation=45)
    plt.yticks(tick_marks, classes)

    if normalize:
        cm = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
        print("Normalized confusion matrix")
    else:
        print('Confusion matrix, without normalization')

    print(cm)

    thresh = cm.max() / 2.
    for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(j, i, cm[i, j],
                 horizontalalignment="center",
                 color="white" if cm[i, j] > thresh else "black")

    plt.tight_layout()
    plt.ylabel('True label')
    plt.xlabel('Predicted label')

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


start_time = time.time()

pathToClassemesNewFile = 'D:\Sharp\Images\Classemes\ClassemesNew.csv'
pathToClassemesNewStylesFile = 'D:\Sharp\Images\Classemes\ClassemesNewStyles.csv'


allSamplesFeatures = [[0 for z in range(512)] for j in range(14258)]
allSamplesDigits = [0 for z in range(14258)]

i = 0
for data in import_text(pathToClassemesNewFile, ','):
    allSamplesFeatures[i] = [float(x) for x in data]
    i += 1

i = 0
for data in import_text(pathToClassemesNewStylesFile, ';'):
    allSamplesDigits[i] = str(data[0])
    i += 1

allSamplesFeatures, allSamplesDigits = shuffleData(allSamplesFeatures, allSamplesDigits)

# Split to trainig, test
X_train, X_test, y_train, y_test = train_test_split(allSamplesFeatures, allSamplesDigits, test_size=0.3, random_state=0)

# Classifier
# http://scikit-learn.org/stable/modules/generated/sklearn.linear_model.SGDClassifier.html

clf = MLPClassifier()

# Set the parameters by cross-validation
iter = [[5,2],[ 10,5],[6, 3], [20,10], [30,5], [40,10]]
tuned_parameters = [{'hidden_layer_sizes': iter}]
sgdCV = GridSearchCV(estimator=clf, param_grid=tuned_parameters, cv=5, verbose=3)
sgdCV.fit(X_train, y_train)
y_predicted = sgdCV.predict(X_train)


print sgdCV.best_params_


# Check results on test set
y_predicted_test = sgdCV.predict(X_test)

scoresGradientBoosting = cross_val_score(sgdCV, allSamplesFeatures, allSamplesDigits)
meanGradientBoostingScore = scoresGradientBoosting.mean()

print (meanGradientBoostingScore)
sgdCV.fit(X_train, y_train)
y_pred = sgdCV.predict(X_test)
print ('Classification report Naive Bayes')
print(classification_report(y_test, y_pred))

print("--- %s seconds ---" % (time.time() - start_time))

print('=====Confusion matrix=====')
cm = confusion_matrix(y_test, y_pred, ['AbstractArt', 'Figurative', 'Pop', 'StreetArt' , 'Hyperrealism'])
print (cm)

# Plot non-normalized confusion matrix
plt.figure()
plot_confusion_matrix(cm, classes=['AbstractArt', 'Figurative', 'Pop', 'StreetArt' , 'Hyperrealism'],
                      title='Confusion matrix')

plt.show()

# y_test_predict = gnb.fit(X_train, y_train).predict(X_test)
#
# print ('Best parameters:')
# print clf.best_params_
# print ('Error cost:')
# print costFunction(Y_test,Y_test_predict)
# print ('Classification report')
# print(classification_report(Y_test, Y_test_predict))
#
# scores = [x[1] for x in clf.grid_scores_]
# plt.figure(1)
# plt.plot(iter, [i * -1 for i in scores], 'ro')
# plt.legend()
# plt.xlabel('Number of iteration')
# plt.ylabel('Error cost')
# plt.show()