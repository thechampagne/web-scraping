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
use std::io::{Error, Read};
use regex::Regex;

fn get_request() -> Result<String, String> {
    match reqwest::blocking::Client::new().get("https://www.imdb.com/chart/top/")
        .send() {
        Ok(mut response) => {
            let mut body = String::new();
            match response.read_to_string(&mut body) {
                Ok(_) => Ok(body),
                Err(err) => Err(err.to_string())
            }
        },
        Err(err) => Err(err.to_string())
    }
}

fn filter() -> Result<Vec<String>, Error> {
    let response= get_request().unwrap();
    let regex = Regex::new("<td class=\"titleColumn\">\\s+(\\d+).\\s+<a href=.+?\\s+.+?>(.*?)</a>").unwrap();
    let mut vector = vec![];
    for cap in regex.captures_iter(&response) {
        vector.push(format!("{}. {}", cap.get(1).unwrap().as_str(), cap.get(2).unwrap().as_str()))
    }
    Ok(vector)
}

fn main() {
    let movies = filter().unwrap();
    for movie in &movies {
        println!("{}", movie)
    }
}