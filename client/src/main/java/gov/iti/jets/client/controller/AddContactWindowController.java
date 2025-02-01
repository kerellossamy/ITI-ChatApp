package gov.iti.jets.client.controller;

import javafx.fxml.FXML;
import javafx.event.*;

import javafx.scene.control.*;




import java.io.IOException;




public class AddContactWindowController {

    
    
      @FXML
      private TextField numberTextField;

   

      @FXML
      public void initialize() 
      {
         
        
      }

    

    @FXML
    public void handleAddContactButton(ActionEvent event) throws IOException
     {
          System.out.println(numberTextField.getText());
    }

    
   
}
