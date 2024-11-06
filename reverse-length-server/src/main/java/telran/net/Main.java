package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 6000;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);
        
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream writer = new PrintStream(socket.getOutputStream());
        ) {
            String request;
            while ((request = reader.readLine()) != null) {
                String[] parts = request.split(" ", 2);
                String type = parts[0].toLowerCase();
                String content = parts.length > 1 ? parts[1] : "";
                
                if ("reverse".equals(type)) {
                    writer.println(new StringBuilder(content).reverse().toString());
                } else if ("length".equals(type)) {
                    writer.println(content.length());
                } else {
                    writer.println("Unknown request type: " + type);
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected unexpectedly");
        }
    }
}
