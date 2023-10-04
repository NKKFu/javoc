package com.simple;

import java.rmi.*;

public interface ClientInterface extends Remote {
    String getName() throws RemoteException;
    void receiveMessage(String message) throws RemoteException;
}
