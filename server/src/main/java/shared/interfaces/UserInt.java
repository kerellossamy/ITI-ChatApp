package shared.interfaces;

import shared.dto.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInt extends Remote {

    void register(ClientInt client) throws RemoteException;

    void unregister(ClientInt client) throws RemoteException;

    boolean isUserFoundByPhoneNumber(String phone_number) throws RemoteException;

    boolean isUserFoundByEmail(String email) throws RemoteException;

    boolean addUsertoDB(User newUser) throws RemoteException;

    User isValidUser(String phoneNumber, String password) throws RemoteException;

    boolean editUserShownInfo(int userId, String name, User.Status status, String picPath, String bio) throws RemoteException;
}