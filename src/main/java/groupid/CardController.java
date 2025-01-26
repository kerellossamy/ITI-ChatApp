/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupid;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Nadam_2kg0od8
 */
public class CardController implements Initializable {

    @FXML
    private Text friendName;
    @FXML
    private Text friendMessage;

    /**
     * Initializes the controller class.
     */

    private String name;
    private String message;


    public CardController() {
        this.name = null;
        this.message = null;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setCard(HBox item)
    {
        VBox vbox =  (VBox) item.getChildren().get(0);
        Text nametext = (Text)vbox.getChildren().get(0);
        Text messagetext = (Text)vbox.getChildren().get(1);

        //System.out.println("name =" + nametext.toString());
        //System.out.println("message =" + messagetext.toString());

        friendName.setText(nametext.getText());
        friendMessage.setText(messagetext.getText());

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
