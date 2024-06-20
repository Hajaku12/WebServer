package edu.escuelaing.arsw;

import com.sun.org.apache.xpath.internal.operations.String;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        ExecutorService pool = Executors.newFixedThreadPool(3);

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Listo para recibir ...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));

            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler (Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String inputLine;
            StringBuilder request = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                request.append(inputLine).append("\n");
                if (inputLine.isEmpty()) {
                    break;
                }
            }
            System.out.println("Received: " + request.toString());

            String outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>Title of the document</title>\n" + "</head>"
                    + "<body>"
                    + "My Web Site"
                    + "</body>"
                    + "</html>";

            out.println(outputLine);

            out.close();

            in.close();

            clientSocket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}