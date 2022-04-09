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
import scala.io.Source
import scala.util.Using

object Main {

  def filter(): List[String] = {
    var response = ""
    Using(Source.fromURL("https://www.imdb.com/chart/top/")) { source =>
      response = source.mkString
    }
    var list: List[String] = List()
    val regex = "<td class=\"titleColumn\">\\s+(\\d+).\\s+<a href=.+?\\s+.+?>(.*?)</a>".r
    for (matcher <- regex.findAllMatchIn(response)) {
      list :+= String.format("%s. %s", matcher.group(1), matcher.group(2))
    }
    list
  }

  def main(args: Array[String]): Unit = {
    for (movie <- filter()) {
      println(movie)
    }
  }
}