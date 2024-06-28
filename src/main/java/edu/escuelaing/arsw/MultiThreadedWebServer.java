package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadedWebServer {
    // To compile and run the project it is necesary to modify the PATH of the location of the file, 
    // it is given the path for the project however it's needed to add the complete path in STATIC_FILES_PATH,
    // DEFAULT_PAGE and MAX_CONNECTIONS_PAGE
    private static final String STATIC_FILES_PATH = "/src/main/java/edu/escuelaing/arsw/static/";
    private static final String DEFAULT_PAGE = "/src/main/java/edu/escuelaing/arsw/static/home.html";
    private static final String MAX_CONNECTIONS_PAGE = "/src/main/java/edu/escuelaing/arsw/static/busy.html";
    private static final int MAX_CONNECTIONS = 3;
    private static final int PORT = 8080;

    private static final Semaphore connectionSemaphore = new Semaphore(MAX_CONNECTIONS);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CONNECTIONS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (connectionSemaphore.tryAcquire()) {
                    executor.submit(new ClientHandler(clientSocket));
                } else {
                    sendBusyResponse(clientSocket);
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static void sendBusyResponse(Socket clientSocket) throws IOException {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String response = new String(Files.readAllBytes(Paths.get(MAX_CONNECTIONS_PAGE)));
            out.println("HTTP/1.1 503 Service Unavailable");
            out.println("Content-Type: text/html");
            out.println();
            out.println(response);
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                handleRequest();
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                connectionSemaphore.release();
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private void handleRequest() throws IOException {
            try {
                // Simulate longer processing time to test the maximun opened server
                Thread.sleep(10000); // 10 second delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String requestLine = in.readLine();
                if (requestLine == null) return;

                String[] requestParts = requestLine.split(" ");
                String path = requestParts[1];

                if (path.startsWith("/fetch")) {
                    handleFetchRequest(path, out);
                } else {
                    serveDefaultPage(out);
                }
            }
        }

        private void handleFetchRequest(String path, PrintWriter out) throws IOException {
            String fileName = STATIC_FILES_PATH + path.split("=")[1];
            String contentType = getContentType(fileName);

            if (contentType.startsWith("image/")) {
                serveImage(fileName, contentType, out);
            } else {
                serveTextFile(fileName, contentType, out);
            }
        }

        private void serveImage(String fileName, String contentType, PrintWriter out) throws IOException {
            byte[] imageData = Files.readAllBytes(Paths.get(fileName));
            String base64Image = Base64.getEncoder().encodeToString(imageData);

            String htmlResponse = "<!DOCTYPE html><html><head><title>Image</title></head><body>" +
                    "<img src=\"data:" + contentType + ";base64," + base64Image + "\" alt=\"Requested Image\">" +
                    "</body></html>";

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            out.println(htmlResponse);
        }

        private void serveTextFile(String fileName, String contentType, PrintWriter out) throws IOException {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType);
            out.println();
            out.println(content);
        }

        private void serveDefaultPage(PrintWriter out) throws IOException {
            String content = new String(Files.readAllBytes(Paths.get(DEFAULT_PAGE)));
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            out.println(content);
        }

        private String getContentType(String fileName) {
            if (fileName.endsWith(".html")) return "text/html";
            if (fileName.endsWith(".js")) return "application/javascript";
            if (fileName.endsWith(".css")) return "text/css";
            if (fileName.endsWith(".txt")) return "text/plain";
            if (fileName.endsWith(".png")) return "image/png";
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
            return "application/octet-stream";
        }
    }
}