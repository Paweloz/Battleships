package com.kodilla.battleship;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/* Klasa zawierajaca zestaw wiadomości
   przekazywanych do wyświetlania w trakcie przebiegu gry
 */
public class Messages {
    private Text placeYourShip = new Text("Please place your ships on your board");

    public Messages(){
        placeYourShip.setFont(Font.font("Verdana", 10));
    }

    public Text getPlaceYourShip() {
        return placeYourShip;
    }
}
