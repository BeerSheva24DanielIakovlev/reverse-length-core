package telran.net;

import telran.view.*;

public class Main {
    private static ReverseClient reverseClient;

    public static void main(String[] args) {
        Item[] items = {
                Item.of("Start Session", Main::startSession),
                Item.ofExit()
        };
        Menu menu = new Menu("Reverse-Length Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname:");
        int port = io.readNumberRange("Enter port:", "Invalid port", 3000, 50000).intValue();
        
        if (reverseClient != null) {
            reverseClient.close();
        }
        reverseClient = new ReverseClient(host, port);

        Menu sessionMenu = new Menu("Run Session",
                Item.of("Reverse String", Main::reverseString),
                Item.of("String Length", Main::stringLength),
                Item.ofExit());
        
        sessionMenu.perform(io);
    }

    static void reverseString(InputOutput io) {
        String str = io.readString("Enter any string:");
        String response = reverseClient.sendAndReceive("reverse " + str);
        io.writeLine("Response from server: " + response);
    }

    static void stringLength(InputOutput io) {
        String str = io.readString("Enter any string:");
        String response = reverseClient.sendAndReceive("length " + str);
        io.writeLine("Response from server: " + response);
    }
}

