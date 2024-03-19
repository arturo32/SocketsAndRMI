package server;

import shared.ChatService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer {
    private static final int PORT = 5431;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ChatService server = new ChatServer();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("ChatServer", server);
        System.out.println("Server started");
    }
}