# ConcurrentTestWebApp

## Overview

ConcurrentTestWebApp is a multithreaded web server application written in Java. It serves HTML files and Base64-encoded image files, capable of handling multiple client connections concurrently. The server limits the number of concurrent clients and provides a custom response when this limit is exceeded.

## Features

- Concurrent client handling using Java threads
- Serves HTML and image files (PNG, JPG, JPEG)
- Base64 encoding for image files
- Configurable maximum client limit
- Custom response for exceeded client connections

## Project Structure

ConcurrentTestWebApp/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── edu/
│       │       └── escuelaing/
│       │           └── arsw/
│       │               └── MultiThreadedWebServer.java
│       │                 
│       └── static/
│           ├── home.html
│           ├── busy.txt
│           └── ... (other resources to test the program)
│
├── pom.xml
└── README.md

## Prerequisites

- Java JDK (version specified in `pom.xml`)
- Apache Maven (version specified in `pom.xml`)

To check your Java version:

'java -version'

To check your Maven version:

'mvn -version'

## Installation

1. Clone the repository:

https://github.com/Hajaku12/WebServer.git

2. Build the project:

'mvn clean package'

## Usage

1. Start the server:

'mvn exec:java -Dexec.mainClass=edu.escuelaing.arsw.MultiThreadedWebServer'


2. Access the web server:
- Open a web browser and navigate to `http://localhost:8080`
- For specific file requests: There are 6 files to test the project with these extensions: jpg, css, html, js, png, txt

## Configuration

- **Additiional Configuration**: To compile and run the project it is necesary to modify the PATH of the location of the file, it is given the path for the project however it's needed to add the complete path in STATIC_FILES_PATH, DEFAULT_PAGE and MAX_CONNECTIONS_PAGE 
- **Port**: 8080
- **Maximum Clients**: 3

## Comparison with Sequential Version

### Initial Sequential Version
- Handled one client at a time
- Blocked while processing requests
- Simple implementation, but inefficient for multiple clients

### Current Concurrent Version
- Handles multiple clients simultaneously
- Non-blocking, improved responsiveness
- Implements thread synchronization
- Includes client limit mechanism

### Key Benefits
- Improved performance and scalability
- Better user experience with reduced delays
- Efficient handling of multiple clients up to the set limit

## Acceptance Test

- Error when the max limit is passed. 

- Reading file with jpg extension. 

- Reading file with css extension.  

- Reading file with html extension.

- Reading file with png extension.

- Reading file with txt extension.


## Deployment Notes

**Important**: Execute the program in the directory containing the files you want to serve.

## Built With

- [Java](https://www.java.com/) - Programming Language
- [Maven](https://maven.apache.org/) - Dependency Management
- HTML5 - Markup Language
- JavaScript - Programming Language
- CSS - Stylesheet Language


## Authors

- Your Name Hann Jang



