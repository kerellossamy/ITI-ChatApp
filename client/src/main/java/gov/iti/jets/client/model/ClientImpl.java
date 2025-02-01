package gov.iti.jets.client.model;//package gov.iti.jets;

import shared.interfaces.ClientInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInt {


   // private HomeSceneController hm;
   //should put the other controllers here;

    private static ClientImpl theOnlyClient;

    private ClientImpl() throws RemoteException {
    }

    public static ClientImpl getInstance()  {

        if(theOnlyClient==null){

            try {
                theOnlyClient=new ClientImpl();
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return theOnlyClient;
    }

//    public void setFm(FirstSceneController fm) {
//        this.fm = fm;
//    }

}
