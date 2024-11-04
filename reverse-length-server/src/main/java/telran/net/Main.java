package telran.net;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 6000;

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> runSession(socket)).start(); 
            }
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {

            String request;
            while ((request = reader.readLine()) != null) {
                String[] parts = request.split(":", 2);
                if (parts.length != 2) {
                    writer.println("Invalid request format. Expected format: <type>:<string>");
                    continue;
                }
                String requestType = parts[0].trim();
                String requestString = parts[1].trim();

                String response = handleRequest(requestType, requestString);
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected or error occurred: " + e.getMessage());
        }
    }

    private static String handleRequest(String requestType, String requestString) {
        switch (requestType.toLowerCase()) {
            case "reverse":
                return new StringBuilder(requestString).reverse().toString();
            case "length":
                return String.valueOf(requestString.length());
            default:
                return "Unknown request type: " + requestType;
        }
    }
}
