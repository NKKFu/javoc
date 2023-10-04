package com.simple;

import java.rmi.*;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverHost;
        int serverPort;

        System.out.println("Welcome to Chat Application!");

        // Ask if the user wants to use a different server host
        System.out.print("Do you want to use a different server host? (y/n): ");
        String response = scanner.nextLine().trim();

        if ("y".equalsIgnoreCase(response)) {
            System.out.print("Enter the server host (default is localhost): ");
            serverHost = scanner.nextLine().trim();
        } else {
            serverHost = "localhost"; // Use default if not specified
        }

        // Ask for the server port
        System.out.print("Enter the server port (default is 1099): ");
        String portInput = scanner.nextLine().trim();

        if (!portInput.isEmpty()) {
            try {
                serverPort = Integer.parseInt(portInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number. Using the default port (1099).");
                serverPort = 1099; // Use default if invalid input
            }
        } else {
            serverPort = 1099; // Use default if not specified
        }

        try {
            String serverURL = "ChatServer";

            // Locate the RMI registry on the server
            Registry registry = LocateRegistry.getRegistry(serverHost, serverPort);

            ServerInterface server = (ServerInterface) registry.lookup(serverURL);

            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            ClientInterface client = new ClientImpl(name, server);

            server.registerClient(client);

            System.out.println("Chat session started. Type your messages (Type 'exit' to quit):");
            String message;
            while (true) {
                message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    server.broadcastMessage(client.getName() + " left the chat.");
                    System.exit(0);
                } else {
                    server.broadcastMessage(client.getName() + ": " + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
