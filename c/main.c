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
#include <stdio.h>
#include <cgo.h>

int main(void)
{

struct get_request_return res = get_request("https://www.imdb.com/chart/top/");
if (res.r1 != 0)
{
	printf("Error: %s", res.r1);
	return -1;
}

char* body = res.r0;

if (body == NULL)
{
	printf("Something went wrong");
	return -1;
}

struct find_all_return rgx = find_all("<td class=\"titleColumn\">\\s+(\\d+).\\s+<a href=.+?\\s+.+?>(.*?)</a>",body);

if (rgx.r1 != 0)
{
	printf("Error: %s", rgx.r1);
	return -1;
}

char** arr = rgx.r0;

if (arr == NULL)
{
	printf("Something went wrong");
	return -1;
}

for (int i = 0; i < 250; i++)
{
	printf("%s\n", arr[i]);
}
return 0;
}