import matplotlib.pyplot as plt

from skimage.feature import hog
from skimage import data, color, exposure
import os
import numpy
import csv

# Read lines from data file.
def import_text(filename, separator):
    for line in csv.reader(open(filename), delimiter=separator,
                           skipinitialspace=True):
        if line:
            yield line

if not os.path.exists("D:\\Sharp\\Images\\AbstractArt\\HOG features"):
    os.makedirs("D:\\Sharp\\Images\\AbstractArt\\HOG features")

pictureName = "chaos-nr-2-1906"

image = color.rgb2gray(data.load("D:\\Sharp\\Images\\AbstractArt\\"+pictureName+".jpg"))

fd, hog_image = hog(image, orientations=8, pixels_per_cell=(16, 16),
                    cells_per_block=(1, 1), visualise=True)
print(fd[1])

f = open("D:\\Sharp\\Images\\AbstractArt\\HOG features\\"+pictureName+".ascii", 'w')
for t in fd:
    f.write(str(t))  # python will convert \n to os.linesep
    f.write("\n")
f.close()  # you can omit in most cases as the destructor will call it

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(8, 4), sharex=True, sharey=True)

ax1.axis('off')
ax1.imshow(image, cmap=plt.cm.gray)
ax1.set_title('Original image')
ax1.set_adjustable('box-forced')

# Rescale histogram for better display
hog_image_rescaled = exposure.rescale_intensity(hog_image, in_range=(0, 0.02))

ax2.axis('off')
ax2.imshow(hog_image_rescaled, cmap=plt.cm.gray)
ax2.set_title('HOG')
ax1.set_adjustable('box-forced')
plt.show()