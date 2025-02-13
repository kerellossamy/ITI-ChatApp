package shared.interfaces;

import shared.dto.BaseMessage;
import shared.dto.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    void playNotificationSound() throws RemoteException;
    public String getPhoneNumber() throws RemoteException;
    public void setPhoneNumber(String phoneNumber) throws RemoteException;
    public void refreshChatList(BaseMessage message,String type,int ID) throws RemoteException;

        //methods to be implemented
    void refreshInvitationList()throws RemoteException;
    void refreshNotificationList()throws RemoteException;
    void refreshContactList(Card c) throws RemoteException ;
    //void refreshContactListWithAnnoncement() throws RemoteException ;

}
