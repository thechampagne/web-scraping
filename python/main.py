# Copyright (c) 2022 XXIV
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, andor sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
import http.client as client
import re

def http():
    try:
        conn = client.HTTPSConnection('www.imdb.com')
        conn.request('GET', '/chart/top/')
        data = conn.getresponse().read().decode('UTF-8')
        conn.close()
        return data
    except Exception as ex:
        raise ex


def filter_html():
    response = http()
    collection = re.findall("<td class=\"titleColumn\">\\s+(\\d+).\\s+<a href=.+?\\s+.+?>(.*?)</a>", response)
    data_list = []
    for matcher in collection:
        data_list.append(f'{matcher[0]}. {matcher[1]}')
    return data_list


if __name__ == '__main__':
    for movie in filter_html():
        print(movie)
