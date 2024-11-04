package telran.net;

import telran.view.*;

public class Main {
    static ReverseClient reverseClient;

    public static void main(String[] args) {
        Item[] items = {
                Item.of("Start session", Main::startSession),
                Item.ofExit()
        };
        Menu menu = new Menu("Reverse Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname:");
        int port = io.readNumberRange("Enter port:", "Invalid port", 3000, 50000).intValue();
        
        if (reverseClient != null) {
            reverseClient.close();
        }
        
        reverseClient = new ReverseClient(host, port);
        
        Menu menu = new Menu("Run Session",
                Item.of("Enter string to reverse", Main::reverseString),
                Item.ofExit()
        );
        menu.perform(io);
    }

    static void reverseString(InputOutput io) {
        String input = io.readString("Enter any string to reverse:");
        String response = reverseClient.sendReverseRequest(input);
        io.writeLine("Reversed string: " + response);
    }
}

