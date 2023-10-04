package com.simple;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private ArrayList<ClientInterface> clients;

    public Server() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void registerClient(ClientInterface client) throws RemoteException {
        if (!clients.contains(client)) {
            clients.add(client);
            broadcastMessage("New user joined the chat.");
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        System.out.println("Broadcasting: " + message);
        for (ClientInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Chat Application! [SERVER EDITION]");
        System.out.print("Enter the server listening address: ");
        String serverHost = scanner.nextLine().trim();

        try {
            Server server = new Server();

            // Create and bind the RMI registry to a specific port (default is 1099)
            System.setProperty("java.rmi.server.hostname", serverHost);
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the server object to the registry
            registry.rebind("ChatServer", server);

            System.out.println("Chat server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
