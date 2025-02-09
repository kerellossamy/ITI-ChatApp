package shared.interfaces;

import shared.dto.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface UserInt extends Remote {

    void register(ClientInt client) throws RemoteException;

    void unregister(ClientInt client) throws RemoteException;

    List<Card> getCards(User user) throws RemoteException;

    List<UserConnection> getUserConncectionById(int userId) throws RemoteException;

    User getUserById(int userId) throws RemoteException;

    boolean isUserFoundByPhoneNumber(String phone_number) throws RemoteException;

    boolean isUserFoundByEmail(String email) throws RemoteException;

    boolean addUsertoDB(User newUser) throws RemoteException;

    User isValidUser(String phoneNumber, String password) throws RemoteException;

    boolean editUserShownInfo(int userId, String name, User.Status status, String picPath, String bio) throws RemoteException;

    User getUserByPhoneNumber(String phone_number) throws RemoteException;

    boolean insertUserConnection(UserConnection userConnection) throws RemoteException;

    boolean addInvitation(Invitation invitation) throws RemoteException;

    Invitation getInvitationBySenderAndReciever(int senderId, int receiverId) throws RemoteException;

    List<GroupMessage> getGroupMessages(int groupId) throws RemoteException;

    List<ServerAnnouncement> getAllServerAnnouncements()throws RemoteException;

    List<DirectMessage> getMessagesBetweenTwo(int receiverId,int senderId) throws RemoteException;

    boolean  insertDirectMessage(DirectMessage directMessage) throws RemoteException;

    void addGroupMessage(GroupMessage message) throws RemoteException;

    UserConnection getUserConnection(int userId, int connectedUserId) throws RemoteException;

    UserBlockedConnection getBlockedConnection(int blockerUserId, int blockedUserId) throws RemoteException;

    int createGroup(String groupName, int createdBy) throws RemoteException;

    void addUserToGroup(int userId, int groupId) throws RemoteException;

    List<Invitation> getAllInvitationsById(int userId) throws RemoteException;

    boolean addUserConnection(UserConnection userConnection) throws RemoteException;

    void deleteInvitation(int invitationId) throws RemoteException;

    boolean isUserConnection(int userId, int connectedUserId) throws RemoteException;

    String getCreatedGroupName(int groupId) throws RemoteException;

    List<Invitation> getAllAcceptedInvitationsBySenderId(int senderId) throws RemoteException;

    List<Invitation> getAllPendingInvitationsByReceiverId(int receiverId) throws RemoteException;

    void updateInvitationStatusById(int invitationId, Invitation.Status newStatus) throws RemoteException;


}