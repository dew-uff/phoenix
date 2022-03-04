# Phoenix

Phoenix is an approach to compare two XML documents and calculate their syntactic similarity. 

# Article 

Alessandreia Marta de Oliveira, Gabriel Tessarolli, Gleiph Ghiotto, Bruno Pinto, Fernando Campello, Matheus Marques, Carlos Roberto Carvalho Oliveira, Igor Rodrigues, Marcos Kalinowski, Uéverton S. Souza, Leonardo Murta, Vanessa Braganholo: An efficient similarity-based approach for comparing XML documents. Inf. Syst. 78: 40-57 (2018). [(Online Version)](https://doi.org/10.1016/j.is.2018.07.001).

# Quick Instalation and Usage Guide 

The most up to date changes were not merged to master, so please switch to the *2.0.x* branch after cloning repo.

```
$ git checkout 2.0.x
```

Make sure you have openjdk-8-jdk and maven installed. Inside the project folder, just run:

```
$ mvn package
```

The resulting package phoenix-2.1.0-SNAPSHOT.jar is in target folder.

The following command you give you the available options:

```
$ java -jar target/phoenix-2.1.0-SNAPSHOT.jar -h
```

Example command, writing the diff result (which is an XML document) of two XML files file1.xml and file2.xml, into a file:

```
$ java -jar target/phoenix-2.1.0-SNAPSHOT.jar file1.xml file2.xml > result.xml
```

#License 

Copyright (c) 2016 Universidade Federal Fluminense (UFF)

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

