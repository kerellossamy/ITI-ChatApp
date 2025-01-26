/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupid;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Nadam_2kg0od8
 */
public class Friend {
    private String name;
    private String message;
    private int numOfMessgae;
    private String messageTime;
    private Image image;
    private Circle status;

    public Friend(String name, String message, int numOfMessgae, String messageTime) {
        this.name = name;
        this.message = message;
        this.numOfMessgae = numOfMessgae;
        this.messageTime = messageTime;
       // this.image = image;
        System.out.println(status);
        this.status.setFill(Color.GREEN);
    }
    
    
    
}
