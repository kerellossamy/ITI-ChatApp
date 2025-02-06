package gov.iti.jets.client.model;

import gov.iti.jets.client.controller.*;
import shared.interfaces.ClientInt;

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
   private HomeScreenController homeScreenController;
   private InvitationListWindowController invitationListWindowController;
   private  ServerHomePageController serverHomePageController;
   private ServerStatusController serverStatusController;
   private StatisticsPieController statisticsPieController;
   private UserLoginController userLoginController;
   private UserSignupController userSignupController;
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

    @Override 
     public  void test () throws RemoteException
     {
        System.out.println("hello from client");
     }

}
