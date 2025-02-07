package gov.iti.jets.server;



import gov.iti.jets.server.model.dao.implementations.*;
import shared.dto.Invitation;
import shared.dto.User;
import shared.dto.UserConnection;
import gov.iti.jets.server.model.dao.interfaces.DirectMessageDAOInt;
import shared.dto.*;
import shared.interfaces.ClientInt;
import shared.interfaces.UserInt;
import shared.utils.DB_UtilityClass;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final UserConnectionDAOImpl userConnectionDAO;
    private final UserDAOImpl userDAO;
    private final UserGroupsDAOImpl userGroupsDAO;
    private Connection connection = DB_UtilityClass.getConnection();

    protected UserImpl() throws RemoteException {

        this.userConnectionDAO=new UserConnectionDAOImpl();
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

    @Override
    public List<Card> getCards(User user) throws RemoteException {

        List<Card> cardList = new ArrayList<>();

        List<UserConnection> listofConnections = userConnectionDAO.getAllConnectionsForUser(user.getUserId());
        System.out.println(listofConnections.size());
        for(UserConnection userConnection : listofConnections)
        {
            Card card =  new Card();
            DirectMessage Message = directMessageDAO.getLastMessageForUser(user.getUserId() ,userConnection.getConnectedUserId());

            System.out.println(Message.getTimestamp());

            User connecterUser = userDAO.getUserById(userConnection.getConnectedUserId());

            System.out.println(connecterUser.getUserId());

            card.setSenderName(connecterUser.getDisplayName());
            card.setStatus(connecterUser.getStatus());
            card.setImagePath(connecterUser.getProfilePicturePath());


           if(Message.getMessageContent() == null)
            {
                card.setMessageContent("");
                card.setTimeStamp(connecterUser.getLastSeen());
            }
            else
            {
                card.setMessageContent(Message.getMessageContent());
                card.setTimeStamp(Message.getTimestamp());
            }


            cardList.add(card);
        }

        /*List<DirectMessage> messageList = directMessageDAO.getLastMessagesForUser(user.getUserId());
        for (DirectMessage message : messageList) {

            Card card = new Card();
            card.setMessageContent(message.getMessageContent());
            card.setTimeStamp(message.getTimestamp());
            User sender = userDAO.getUserById(message.getSenderId());
            card.setStatus(sender.getStatus());
            card.setSenderName(sender.getDisplayName());
            card.setImagePath(sender.getProfilePicturePath());

            cardList.add(card);
        }
*/
        try {

            List<UserGroups> groupsList = userGroupsDAO.getGroupsByUserId(user.getUserId());
            for (UserGroups group : groupsList) {
                Card card = new Card();
                GroupMessage groupMessage = groupMessageDAO.getLatestMessageInGroup(group.getGroupId());
                card.setMessageContent(groupMessage.getMessageContent());
                card.setTimeStamp(groupMessage.getTimestamp());
               // User sender = userDAO.getUserById(groupMessage.getSenderId());
                card.setStatus(User.Status.AVAILABLE);
                card.setSenderName(groupDAO.getGroupNameById(group.getGroupId()));
                card.setImagePath("src/main/resources/img/people.png");
                cardList.add(card);
            }

            ServerAnnouncement serverAnnouncement = serverAnnouncementDAO.getLatestAnnouncement();
            if (serverAnnouncement != null) {
                Card announcementCard = new Card();
                announcementCard.setTimeStamp(serverAnnouncement.getCreatedAt());
                announcementCard.setMessageContent(serverAnnouncement.getMessage());
                announcementCard.setSenderName("TAWASOL");
                announcementCard.setStatus(User.Status.AVAILABLE);
                announcementCard.setImagePath("src/main/resources/img/setting.png");
                cardList.add(announcementCard);
            }

        }
        catch (SQLException ex)
        {
            System.out.println("SQL Exception");
        }


        cardList.sort(Comparator.comparing(Card::getTimestamp, Comparator.nullsLast(Comparator.reverseOrder())));
        return cardList;
    }

    // List<UserConnection> getAllConnectionsForUser(int userId);
    @Override
    public  List<UserConnection> getUserConncectionById(int userId) throws RemoteException
    {      System.out.println("hello");
          return userConnectionDAO.getAllConnectionsForUser(userId);
    }

    @Override
    public  User getUserById(int userId) throws RemoteException
    {
          return userDAO.getUserById(userId);
    }

    public static int getOnlineUsers()
    {
        return OnlineClintsList.size();
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

    @Override
    public List<GroupMessage> getGroupMessages(int groupId) throws RemoteException {
        try {
            return groupMessageDAO.getGroupMessageByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<ServerAnnouncement> getAllServerAnnouncements()  {
        try {
            return serverAnnouncementDAO.getAllServerAnnouncements();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DirectMessage> getMessagesBetweenTwo(int receiverId, int senderId) throws RemoteException {
        return directMessageDAO.getMessagesBetweenTwo(receiverId, senderId);
    }

    @Override
    public boolean insertDirectMessage(DirectMessage directMessage) {
        try {
            directMessageDAO.insertDirectMessage(directMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public void addGroupMessage(GroupMessage message) throws RemoteException {

        try {
            groupMessageDAO.addGroupMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserConnection getUserConnection(int userId, int connectedUserId) throws RemoteException {
        return userConnectionDAO.getUserConnection(userId, connectedUserId);
    }

    @Override
    public UserBlockedConnection getBlockedConnection(int blockerUserId, int blockedUserId) throws RemoteException {
        return userBlockedConnectionDAO.getBlockedConnection(blockerUserId, blockedUserId);
    }


}
