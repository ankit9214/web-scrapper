#  Go Comics Web Scrapper

This is a very basic Web Scrapper written in Java and uses jsoup API which downloads comics from GoComics website.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine. 

### Prerequisites

1. Basic Java Knowledge like how to compile and run a Java Program
2. JDK 1.8 +
2. An IDE/Text Editor

### Running

Update Constants.java file with root url of comics and the date range for which you want to download it.
For e.g.

```
public static final String SCRAPPING_URL = "https://www.gocomics.com/calvinandhobbes/";
```
```
public static final String START_DATE = "1985/11/18";
```
```
public static final String END_DATE = "1995/12/31";
```

## Built With

* [Java](https://www.java.com/en/) - Programming Language
* [Maven](https://maven.apache.org/) - Dependency Management
* [jsoup](https://jsoup.org/) - Java HTML parser


