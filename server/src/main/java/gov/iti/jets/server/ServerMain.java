package gov.iti.jets.server;

import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {

    public ServerMain() {
        try{

            UserInt userInt=new UserImpl();
            AdminInt adminInt=new AdminImpl();
            Registry registry= LocateRegistry.createRegistry(8554);
            registry.rebind("UserServices",adminInt);
            registry.rebind("AdminServices",adminInt);
            System.out.println("server is running .....");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //new ServerMain();
        System.out.println("Hello from the server");
    }
}
