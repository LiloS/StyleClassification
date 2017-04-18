from bs4 import BeautifulSoup
import urllib2
# import httplib2
import webbrowser
import re

# html_page = urllib2.urlopen("https://www.artsy.net/collect?page=1&medium=painting&gene_id=pop-and-contemporary-pop&sort=year")
# soup = BeautifulSoup(html_page)
#
# for item in soup.find_all(attrs={'class': 'artwork-item-image-link'}):
#     print(item)
#     for link in item.find_all('a'):
#         print link.get('href')
# for link in soup.findAll('a'):
#     print link.get('href')

# <div class="artwork-item-image-container"><a href="/artwork/christophe-streichenberger-summertime" class="artwork-item-image-link"><img src="https://d32dm0rphc51dk.cloudfront.net/lFY2VIcYhC5w-1YAslMOqg/tall.jpg" nopin="nopin" alt=", 'Summertime ,' 16, Galerie Art Jingle" class="artwork-item-image"><div class="overlay-container"><div class="overlay-button-save"></div></div></a></div>
#
#

url = 'http://docs.python.org/'
chrome_path = 'C:\Program Files (x86)\Google\Chrome\Application\chrome.exe %s'

webbrowser.get(chrome_path).open(url)


pth = "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
webbrowser.register('chrome', None, webbrowser.BackgroundBrowser(pth))
chrome = webbrowser.get('chrome')


for number in range(40,57):
    urlPatter = 'https://www.artsy.net/collect?page='+str(number)+'&medium=painting&gene_id=abstract-art'
    urlPatter = 'https://www.artsy.net/collect?page='+str(number)+'&medium=painting&gene_id=pop-and-contemporary-pop&sort=year'
    urlPatter = 'https://www.artsy.net/collect?page='+str(number)+'&medium=painting&gene_id=figurative-art&sort=year'
    urlPatter = 'https://www.artsy.net/collect?page='+str(number)+'&medium=painting&gene_id=hyperrealism-and-photorealism&sort=year'
    urlPatter = 'https://www.artsy.net/collect?page='+str(number)+'&medium=painting&gene_id=graffiti-slash-street-art&sort=year'
    chrome.open_new_tab(urlPatter)