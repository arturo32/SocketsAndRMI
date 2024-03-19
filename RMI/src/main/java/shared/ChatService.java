package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    String talk(String message) throws RemoteException;
}
