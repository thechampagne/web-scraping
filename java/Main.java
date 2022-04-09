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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Main {

    static String getRequest() throws IOException {
        URL url = new URL("https://www.imdb.com/chart/top/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader;
        if (100 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        StringBuilder string = new StringBuilder();
        String output;
        while ((output = reader.readLine()) != null) {
            string.append(output);
        }
        return string.toString();
    }

    static List<String> filter() throws IOException {
        String response = getRequest();
        Pattern pattern = Pattern.compile("<td class=\\\"titleColumn\\\">\\s+(.+?).\\s+<a href=.+?>(.+?)</a>.+?</td>");
        Matcher matcher = pattern.matcher(response);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(String.format("%s. %s", matcher.group(1), matcher.group(2)));
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        for (String movie : filter()) {
            System.out.println(movie);
        }
    }
}