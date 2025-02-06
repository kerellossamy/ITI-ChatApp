package shared.interfaces;

import shared.dto.Card;
import shared.dto.User;
import shared.dto.UserConnection;

import shared.dto.Invitation;
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

    boolean isUserFoundByPhoneNumber(String phone_number) throws RemoteException;

    boolean isUserFoundByEmail(String email) throws RemoteException;

    boolean addUsertoDB(User newUser) throws RemoteException;

    User isValidUser(String phoneNumber, String password) throws RemoteException;

    boolean editUserShownInfo(int userId, String name, User.Status status, String picPath, String bio) throws RemoteException;

     User getUserByPhoneNumber(String phone_number) throws RemoteException;
     boolean insertUserConnection(UserConnection userConnection)throws  RemoteException;
    boolean addInvitation(Invitation invitation)throws RemoteException;
    Invitation getInvitationBySenderAndReciever(int senderId, int receiverId) throws RemoteException;

}