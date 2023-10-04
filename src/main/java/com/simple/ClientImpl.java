package com.simple;

import java.rmi.*;
import java.rmi.server.*;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
    private String name;
    private ServerInterface server;

    public ClientImpl(String name, ServerInterface server) throws RemoteException {
        this.name = name;
        this.server = server;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
