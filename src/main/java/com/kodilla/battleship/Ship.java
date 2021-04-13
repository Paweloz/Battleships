package com.kodilla.battleship;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/* Klasa odpowiadająca za tworzenie statku
*/

public class Ship extends Rectangle {
    private final int type;
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean move = false;
    private Group ship = new Group();
//    double Y=ship.getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
//    double X=ship.getBoundsInLocal().getWidth();


// Konstruktor przyjmuje wielkość statku oraz jego kolor,
// a następnie tworzy odpowiednią ilość kwadratów o zadanym rozmiarze i dodaje je do grupy
    public Ship(double width, double height, Paint fill, int type) {
        super(width,height,fill);
        this.width = width;
        this.height = height;
        this.type = type;

        for(int i=0;i<type;i++){
            Rectangle r = new Rectangle(width,height,fill);
            r.setX(i*width+1);
            ship.getChildren().add(r);
        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Obsługa eventów press drag release dla grupy kwadratów tworzących statek
        ship.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ship.setMouseTransparent(true);
                event.setDragDetect(true);
                x = ship.getTranslateX() - event.getSceneX();
                y = ship.getTranslateY() - event.getSceneY();

            } else {
                ship.setRotate(getRotate()+90);
        }});

        ship.setOnDragDetected(event -> {
            ship.startFullDrag();
        });


        ship.setOnMouseDragged(event -> {

            ship.setTranslateX(event.getSceneX() + x);
            ship.setTranslateY(event.getSceneY() + y);
        });

        ship.setOnMouseReleased(event -> {
            ship.setMouseTransparent(false);

        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Group getShip() {
        return ship;
    }

    public double getShipWidth() {
        return width;
    }

    public double getShipHeight() {
        return height;
    }
}
