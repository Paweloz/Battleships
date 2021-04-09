package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Ship extends Rectangle {
    private final int type;
    Group ship = new Group();

    public Ship(double width, double height, Paint fill, int type) {
        super(width, height, fill);
        this.type = type;

        for(int i=0;i<type;i++){
            Rectangle r = new Rectangle(width,height,fill);
            r.setX(i*width+1);
            ship.getChildren().add(r);
        }

        //Wstępna próba obsługi eventu dla całej grupy kwadratów.
        ship.setOnMouseClicked(event ->{
            for (int i=0 ; i<ship.getChildren().size();i++){
                Rectangle r = (Rectangle) ship.getChildren().get(i);
                r.setFill(Color.RED);
            }
        });
    }

    public Group getShip() {
        return ship;
    }
}
