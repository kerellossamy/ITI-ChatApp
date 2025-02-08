package shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    void playNotificationSound() throws RemoteException;
    public String getPhoneNumber() throws RemoteException;
    public void setPhoneNumber(String phoneNumber) throws RemoteException;

}
