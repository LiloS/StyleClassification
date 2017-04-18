import os
import re
import sys
import lxml.html
from lxml.cssselect import CSSSelector
import requests
import wget

link = CSSSelector(".js-artwork-images__images__image__display__img")
linkRegex = re.compile('img data-src="(.+?)"', re.UNICODE)


# Загрузка страницы
def download_webpage(url):
    r = requests.get(url)
    if r.status_code != 200:
        raise Exception
    return r


# Парсинг страницы
def parsePage(requests_object):
    return lxml.html.fromstring(requests_object.text)


# Извлечение ссылки на изображение
def extractLink(tree):
    link_text = lxml.html.tostring(link(tree)[0]).decode("utf-8")
    return linkRegex.search(link_text).group(1)


# Загрузка изображения
def downloadImage(link, pathToFile):
    filename = wget.download(link, pathToFile)
    return filename


def create_subdirectories_to_place_artwork_image(path):
    os.makedirs(path, exist_ok=True)
    return True


def rename_downloaded_artwork_image(old_filename, new_filename):
    tmp, extension = os.path.splitext(old_filename)
    has_path = SEP_DIRS.search(new_filename)
    if has_path:
        create_subdirectories_to_place_artwork_image(has_path.group(1))
    os.rename(old_filename, "{}{}".format(new_filename, extension))
    return True


def main():
    pathToLinksFile = 'D:\Sharp\Images\Hyperrealism\A\\Links.csv'
    i = 0
    for data in import_text(pathToLinksFile, ','):

        THE_URL = data[0]
        try:
            tree = parse_webpage(download_webpage(THE_URL))
            link = extract_artwork_image_link_from_webpage(tree)
            download_artwork_image(link, "H:\\Paintings\\Hyperrealism\\" + str(i) + ".png")
        except:
            print("Error " + str(i))
        i += 1

    pathToLinksFile = 'D:\Sharp\Images\Street art\A\\Links.csv'
    i = 0
    for data in import_text(pathToLinksFile, ','):

        THE_URL = data[0]
        try:
            tree = parse_webpage(download_webpage(THE_URL))
            link = extract_artwork_image_link_from_webpage(tree)
            download_artwork_image(link, "H:\\Paintings\\Street art\\" + str(i) + ".png")
        except:
            print("Error " + str(i))
        i += 1

    return True


if __name__ == '__main__':
    STATUS = main()
    sys.exit(STATUS)
