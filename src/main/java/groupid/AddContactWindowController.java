package groupid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import java.io.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.BorderPane;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.*;
import java.util.concurrent.*;


//io
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.*;

//stage
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
