package gov.iti.jets.server;



import gov.iti.jets.server.model.dao.implementations.*;
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

    List<ClientInt> OnlineClintsList =new ArrayList<>();
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
        this.userConnectionDAO = new UserConnectionDAOImpl();
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
}
