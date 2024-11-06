package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ReverseClient {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public ReverseClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to server", e);
        }
    }

    public String sendAndReceive(String request) {
        try {
            writer.println(request);
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException("Error during communication with server", e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException("Error closing client connection", e);
        }
    }
}
