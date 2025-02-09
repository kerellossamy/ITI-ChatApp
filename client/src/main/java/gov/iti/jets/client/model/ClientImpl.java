package gov.iti.jets.client.model;

import gov.iti.jets.client.controller.*;
import shared.dto.BaseMessage;
import shared.interfaces.ClientInt;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInt {


   private AddContactWindowController addContactWindowController;
   private AdminLoginController adminLoginController;
   private AdminSignupController adminSignupController;
   private AnnouncementController announcementController;
   private BlockContactsController blockContactsController;
   private CardController cardController;
   private CountryPieController countryPieController;
   private CreateGroupController createGroupController;
   private EditWindowController editWindowController;
   private GenderPieController genderPieController;
   private GeneralStatisticsController generalStatisticsController;
    public   static HomeScreenController homeScreenController;
   private  InvitationListWindowController invitationListWindowController;
   private  ServerHomePageController serverHomePageController;
   private ServerStatusController serverStatusController;
   private StatisticsPieController statisticsPieController;
   private UserLoginController userLoginController;
   private UserSignupController userSignupController;
   private GroupProfileController groupProfileController;
   private static ClientImpl theOnlyClient;
   private static String phoneNumber;


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

    public void setAddContactWindowController(AddContactWindowController addContactWindowController) {
        this.addContactWindowController = addContactWindowController;
    }

    public void setAdminLoginController(AdminLoginController adminLoginController) {
        this.adminLoginController = adminLoginController;
    }

    public void setAdminSignupController(AdminSignupController adminSignupController) {
        this.adminSignupController = adminSignupController;
    }

    public void setAnnouncementController(AnnouncementController announcementController) {
        this.announcementController = announcementController;
    }

    public void setBlockContactsController(BlockContactsController blockContactsController) {
        this.blockContactsController = blockContactsController;
    }

    public void setCardController(CardController cardController) {
        this.cardController = cardController;
    }

    public void setCountryPieController(CountryPieController countryPieController) {
        this.countryPieController = countryPieController;
    }

    public void setCreateGroupController(CreateGroupController createGroupController) {
        this.createGroupController = createGroupController;
    }

    public void setEditWindowController(EditWindowController editWindowController) {
        this.editWindowController = editWindowController;
    }

    public void setGenderPieController(GenderPieController genderPieController) {
        this.genderPieController = genderPieController;
    }

    public void setGeneralStatisticsController(GeneralStatisticsController generalStatisticsController) {
        this.generalStatisticsController = generalStatisticsController;
    }

    public void setHomeScreenController(HomeScreenController homeScreenController) {
        this.homeScreenController = homeScreenController;
    }

    public void setInvitationListWindowController(InvitationListWindowController invitationListWindowController) {
        this.invitationListWindowController = invitationListWindowController;
    }

    public void setServerHomePageController(ServerHomePageController serverHomePageController) {
        this.serverHomePageController = serverHomePageController;
    }

    public void setServerStatusController(ServerStatusController serverStatusController) {
        this.serverStatusController = serverStatusController;
    }

    public void setStatisticsPieController(StatisticsPieController statisticsPieController) {
        this.statisticsPieController = statisticsPieController;
    }

    public void setUserLoginController(UserLoginController userLoginController) {
        this.userLoginController = userLoginController;
    }

    public void setUserSignupController(UserSignupController userSignupController) {
        this.userSignupController = userSignupController;
    }

    public void setGroupProfileController(GroupProfileController groupProfileController) {
        this.groupProfileController = groupProfileController;
    }


    @Override
    public void playNotificationSound() {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("notification.wav");

            if (inputStream == null) {
                System.err.println("Audio file not found in resources.");
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPhoneNumber()  {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void refreshChatList(BaseMessage message) throws RemoteException {

        homeScreenController.refreshChatList(message);

    }

    @Override
    public void refreshInvitationList() throws RemoteException {

            //put implementation here


    }

    @Override
    public void refreshNotificationList() throws RemoteException {
        //put implementation here
    }

    @Override
    public void refreshContactList() throws RemoteException {

        //put implementation here
    }


}


