package telran.net;

import java.net.*;
import java.io.*;

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
            throw new RuntimeException(e);
        }
    }

    public String sendReverseRequest(String input) {
        try {
            writer.println("reverse:" + input);
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}