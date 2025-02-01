package gov.iti.jets.server;


import shared.interfaces.AdminInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AdminImpl extends UnicastRemoteObject implements AdminInt {


    public AdminImpl() throws RemoteException {

    }
}

