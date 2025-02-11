package shared.interfaces;

import shared.dto.BaseMessage;
import shared.dto.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    void playNotificationSound() throws RemoteException;
    String getPhoneNumber() throws RemoteException;

    void setPhoneNumber(String phoneNumber) throws RemoteException;
    void refreshChatList(BaseMessage message) throws RemoteException;

        //methods to be implemented
    void refreshInvitationList()throws RemoteException;
    void refreshNotificationList()throws RemoteException;
    void refreshContactList(Card c) throws RemoteException ;

}
