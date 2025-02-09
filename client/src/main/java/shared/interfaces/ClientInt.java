package shared.interfaces;

import shared.dto.BaseMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    void playNotificationSound() throws RemoteException;
    public String getPhoneNumber() throws RemoteException;
    public void setPhoneNumber(String phoneNumber) throws RemoteException;
    public void refreshChatList(BaseMessage message) throws RemoteException;


    //methods to implement
    public void refreshInvitationList()throws RemoteException;
    public void refreshNotificationList()throws RemoteException;
    public void refreshContactList()throws RemoteException;

}
