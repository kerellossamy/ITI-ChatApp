package gov.iti.jets.server;


import gov.iti.jets.server.model.dao.implementations.*;
import shared.dto.Invitation;
import shared.dto.User;
import shared.dto.UserConnection;
import shared.interfaces.ClientInt;
import shared.interfaces.UserInt;
import shared.utils.DB_UtilityClass;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserImpl extends UnicastRemoteObject implements UserInt {

    List<ClientInt> OnlineClintsList = new ArrayList<>();
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
    private final UserConnectionDAOImpl userConnectionDAO;
    private Connection connection = DB_UtilityClass.getConnection();

    protected UserImpl() throws RemoteException {

        this.userConnectionDAO=new UserConnectionDAOImpl();
        this.adminDAO = new AdminDAOImpl(connection);
        this.chatbotDAO = new ChatbotDAOImpl(connection);
        this.directMessageDAO = new DirectMessageDAOImpl();
        this.fileTransferDAO = new FileTransferDAOImpl();
        this.groupDAO = new GroupDAOImpl(connection);
        this.groupMessageDAO = new GroupMessageDAOImpl(connection);
        this.invitationDAO = new InvitationDAOImpl();
        this.serverAnnouncementDAO = new ServerAnnouncementDAOImpl(connection);
        this.socialNetworkDAO = new SocialNetworkDAOImpl(connection);
        this.userBlockedConnectionDAO = new UserBlockedConnectionDAOImpl();
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

    @Override
    public boolean isUserFoundByPhoneNumber(String phone_number) {
        return userDAO.isUserFoundByPhoneNumber(phone_number);
    }

    @Override
    public boolean isUserFoundByEmail(String email) throws RemoteException {
        return userDAO.isUserFoundByEmail(email);
    }

    @Override
    public boolean addUsertoDB(User newUser) throws RemoteException {
        return userDAO.createUserWithoutID(newUser);
    }

    @Override
    public User isValidUser(String phoneNumber, String password) throws RemoteException {
        return userDAO.getUserByPhoneNumberAndPassword(phoneNumber, password);
    }

    @Override
    public boolean editUserShownInfo(int userId, String name, User.Status status, String picPath, String bio) throws RemoteException {
        System.out.println("for testing i'm in editUserShownInfo in the user impl");

        return userDAO.editUserShownInfo(userId, name, status, picPath, bio);
    }

   public User getUserByPhoneNumber(String phone_number) {

        return userDAO.getUserByPhoneNumber(phone_number);
    }

    @Override
    public boolean insertUserConnection(UserConnection userConnection) throws RemoteException {
        return userConnectionDAO.insertUserConnection(userConnection);
    }

    @Override
    public boolean addInvitation(Invitation invitation) throws RemoteException {
       return invitationDAO.addInvitation(invitation);
    }

    @Override
    public Invitation getInvitationBySenderAndReciever(int senderId, int receiverId) throws RemoteException {
        return invitationDAO.getInvitationBySenderAndReciever(senderId, receiverId);
    }
}
