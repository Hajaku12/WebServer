# Concurrent Web Server

## Overview

ConcurrentTestWebApp is a multithreaded web server application written in Java. It serves HTML files and Base64-encoded image files, capable of handling multiple client connections concurrently. The server limits the number of concurrent clients and provides a custom response when this limit is exceeded.

## Features

- Concurrent client handling using Java threads
- Serves HTML and image files (PNG, JPG, JPEG)
- Base64 encoding for image files
- Configurable maximum client limit
- Custom response for exceeded client connections

## Project Structure

<img width="435" alt="Structure" src="https://github.com/Hajaku12/WebServer/assets/62758651/5a3c7852-dfc7-4548-93f1-873c230d2923">


## Prerequisites

- Java JDK (version specified in `pom.xml`)
- Apache Maven (version specified in `pom.xml`)

To check your Java version: `java -version`

To check your Maven version: `mvn -version`

## Installation

1. Clone the repository:

https://github.com/Hajaku12/WebServer.git

2. Build the project: `mvn clean package`

## Usage

1. Start the server: `mvn exec:java -Dexec.mainClass=edu.escuelaing.arsw.MultiThreadedWebServer`


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

  <img width="1447" alt="image1" src="https://github.com/Hajaku12/WebServer/assets/62758651/0aadbf68-6f1c-4e66-85fd-9ad4ed551333">


- Reading file with jpg extension.

  <img width="967" alt="image2" src="https://github.com/Hajaku12/WebServer/assets/62758651/53e74f6d-f5c9-428d-a405-bb04c2762051">


- Reading file with css extension.

  <img width="1057" alt="image3" src="https://github.com/Hajaku12/WebServer/assets/62758651/7a2b29c5-16ab-4eff-a4fe-57af07ec3e48">

- Reading file with html extension.
  
  <img width="1065" alt="image5" src="https://github.com/Hajaku12/WebServer/assets/62758651/e34ebaa8-a42e-4699-ad24-d664d4b4c3f7">

- Reading file with png extension.

  <img width="1056" alt="image6" src="https://github.com/Hajaku12/WebServer/assets/62758651/c296663d-1306-4459-9a58-db1028776c7e">

- Reading file with txt extension.

  <img width="800" alt="image7" src="https://github.com/Hajaku12/WebServer/assets/62758651/6528a3f4-3fd2-4221-902d-e5043aa35503">

- Reading file with js extensions.

  <img width="783" alt="image4" src="https://github.com/Hajaku12/WebServer/assets/62758651/e049099f-e182-4a4a-9406-defec7d50dc9">


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



