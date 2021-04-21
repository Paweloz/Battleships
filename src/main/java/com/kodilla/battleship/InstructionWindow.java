package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class InstructionWindow {
    Image imageback = new Image("file:src/main/resources/battleship.gif");
    BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
    BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    Background background = new Background(backgroundImage);

    Text instruction = new Text("PLACING SHIPS\n\nUse left mouse button to drag and drop your ship on board\n" +
            "You can rotate your ship with right mouse button\nOnce you drop ship onto a board, you cannot change it's position\n" +
            "You need to have atleast one space gap between your ships\n\n" +
            "SHOOTING\n\nYou have to place all the ships on board, before start shooting\nWhen you finish placing ships, press" +
            " \"start\"button to begin\nYou shot first. If anyone hit the ship,\n" +
            " you keeps shooting, unitl you miss\n\n Game is over when one of the players lost all the ships\n\n\n" +
            "GOOD LUCK ;)");
    BorderPane layout = new BorderPane();
    StackPane centerPane = new StackPane();


    public InstructionWindow(){
        centerPane.getChildren().add(instruction);
        centerPane.setAlignment(Pos.TOP_CENTER);
        centerPane.setPadding(new Insets(50,20,20,50));

        layout.setLeft(centerPane);
        layout.setBackground(background);
    }

    public BorderPane getLayout() {
        return layout;
    }
}
