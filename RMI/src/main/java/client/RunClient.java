package client;

import shared.ChatService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RunClient     {
    public static void main(String[] args) {
    try {
        ChatClient client = new ChatClient();
        client.startClient();

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        ChatService chatService = (ChatService) registry.lookup("ChatServer");

        Scanner scanner = new Scanner(System.in);
        String message = "";

        while (!message.equals("bye")) {
            System.out.print("Me: ");
            message = scanner.nextLine();
            String response = chatService.talk(message);
            System.out.println("Alice: " + response);
        }

        System.out.println("Finished.");

    } catch (Exception e) {
        System.err.println("Error: " + e.toString());
        e.printStackTrace();
    }
}
}