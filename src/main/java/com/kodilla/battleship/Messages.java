package com.kodilla.battleship;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Messages {
    private Text placeYourShip = new Text("Please place your ships on your board");

    public Messages(){
        placeYourShip.setFont(Font.font("Verdana", 10));
    }

    public Text getPlaceYourShip() {
        return placeYourShip;
    }
}
