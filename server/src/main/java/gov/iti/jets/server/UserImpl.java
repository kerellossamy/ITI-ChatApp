package gov.iti.jets.server;



import gov.iti.jets.server.model.dao.implementations.*;
import shared.interfaces.ClientInt;
import shared.interfaces.UserInt;
import shared.utils.DB_UtilityClass;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserImpl extends UnicastRemoteObject implements UserInt {
  
   
   static List<ClientInt> OnlineClintsList =new ArrayList<>();
    private final AdminDAOImpl adminDAO;
    private final ChatbotDAOImpl chatbotDAO;
    private final DirectMessageDAOImpl directMessageDAO;
    private final FileTransferDAOImpl fileTransferDAO;
    private final GroupDAOImpl groupDAO;
    private final GroupMessageDAOImpl groupMessageDAO;
    private final InvitationDAOImpl invitationDAO;
    private final ServerAnnouncementDAOImpl serverAnnouncementDAO;
    private final SocialNetworkDAOImpl socialNetworkDAO;
    private final UserBlockedConnectionDAOImpl userBlockedConnectionDAO;
    private final UserDAOImpl userDAO;
    private final UserGroupsDAOImpl userGroupsDAO;
    private Connection connection  = DB_UtilityClass.getConnection();

    protected UserImpl() throws RemoteException {

        this.adminDAO = new AdminDAOImpl(connection);
        this.chatbotDAO = new ChatbotDAOImpl(connection);
        this.directMessageDAO = new DirectMessageDAOImpl();
        this.fileTransferDAO = new FileTransferDAOImpl();
        this.groupDAO = new GroupDAOImpl(connection);
        this.groupMessageDAO =new GroupMessageDAOImpl(connection);
        this.invitationDAO = new InvitationDAOImpl();
        this.serverAnnouncementDAO = new ServerAnnouncementDAOImpl(connection);
        this.socialNetworkDAO = new SocialNetworkDAOImpl(connection);
        this.userBlockedConnectionDAO =new UserBlockedConnectionDAOImpl();
        this.userDAO = new UserDAOImpl();
        this.userGroupsDAO = new UserGroupsDAOImpl(connection);
    }
    
   

    @Override
    public void register(ClientInt client) throws RemoteException {
        
        OnlineClintsList.add(client);


    }

    @Override
    public void unregister(ClientInt client) throws RemoteException {

        OnlineClintsList.remove(client);
    }
   
    public static int getOnlineUsers()
    {
        return OnlineClintsList.size();
    }
}
