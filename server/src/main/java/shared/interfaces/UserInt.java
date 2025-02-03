package shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInt extends Remote {

    void register(ClientInt client) throws RemoteException;
    void unregister(ClientInt client) throws RemoteException;
}
