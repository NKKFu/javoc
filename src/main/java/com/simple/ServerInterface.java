package com.simple;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void registerClient(ClientInterface client) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
}
