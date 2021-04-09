package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class InstructionWindow {
    Image imageback = new Image("file:C:\\Development\\Projects\\BattleshipSimple\\src\\main\\resources\\battleship.gif");
    BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
    BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    Background background = new Background(backgroundImage);

    Text instruction = new Text("Instrukcja do gry.................\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.");
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
