package gov.iti.jets.server;



import shared.interfaces.ClientInt;
import shared.interfaces.UserInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class UserImpl extends UnicastRemoteObject implements UserInt {

    List<ClientInt> OnlineClintsList =new ArrayList<>();

    protected UserImpl() throws RemoteException {
    }


    @Override
    public void register(ClientInt client) throws RemoteException {

        OnlineClintsList.add(client);

    }

    @Override
    public void unregister(ClientInt client) throws RemoteException {

        OnlineClintsList.remove(client);
    }
}
