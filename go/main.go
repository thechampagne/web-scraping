/*
 * Copyright (c) 2022 XXIV
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"regexp"
)

func getRequest() (string, error) {
	response, err := http.Get("https://www.imdb.com/chart/top/")
	if err != nil {
		return "", err
	}
	defer response.Body.Close()
	body, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return "", err
	}
	return string(body), nil
}

func filter() ([]string, error) {
	response, err := getRequest()
	if err != nil {
		return []string{}, err
	}
	regex, err := regexp.Compile(`<td class="titleColumn">\s+(\d+).\s+<a href=.+?\s+.+?>(.*?)</a>`)
	if err != nil {
		return []string{}, err
	}
	matcher := regex.FindAllStringSubmatch(response, -1)
	var slice []string
	for i := range matcher{
		slice= append(slice,fmt.Sprintf("%s. %s", matcher[i][1], matcher[i][2]))
	}
	return slice, nil
}

func main() {
	movies, err := filter()
	if err != nil {
		log.Fatal(err)
	}
	for _, movie := range movies {
		fmt.Println(movie)
	}
}