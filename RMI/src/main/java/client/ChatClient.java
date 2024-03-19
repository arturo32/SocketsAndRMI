package client;

import shared.ChatService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient implements ChatService {
    private ChatService server;

    public ChatClient() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    public void startClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        server = (ChatService)registry.lookup("ChatServer");
    }


    @Override
    public String talk(String message) throws RemoteException {
        return this.server.talk(message);
    }
}
