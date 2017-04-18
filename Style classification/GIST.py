from PIL import Image
import pimpy.image.features.feature

im = Image.open('lear_gist/ar.ppm')
descriptors = leargist.color_gist(im)

descriptors.shape
descriptors.dtype

descriptors[:4]