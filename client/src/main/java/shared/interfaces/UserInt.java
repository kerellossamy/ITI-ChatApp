package shared.interfaces;

import shared.dto.Card;
import shared.dto.User;
import shared.dto.UserConnection;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserInt extends Remote {

    void register(ClientInt client) throws RemoteException;
    void unregister(ClientInt client) throws RemoteException;
    List<Card> getCards(User user) throws RemoteException;
    List<UserConnection> getUserConncectionById(int userId) throws RemoteException;
    User getUserById(int userId) throws RemoteException;

}
