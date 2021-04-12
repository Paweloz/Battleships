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
    private Pane shipArena = new Pane();
    private MakeDragable makeDragable = new MakeDragable();

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
        this.shipArena.getChildren().add(ship);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Obsługa eventów press drag release dla grupy kwadratów tworzących statek
        ship.setOnMousePressed(event -> {
            move = true;
            if (event.getButton() == MouseButton.PRIMARY) {
                x = ship.getTranslateX() - event.getSceneX();
                y = ship.getTranslateY() - event.getSceneY();
                System.out.println("Ships x " + x + " and y " + y);
                double Y=ship.getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
                double X=ship.getBoundsInLocal().getWidth();
                System.out.println("Ship height = "+Y+" Ship width = "+X);
            } else {
                System.out.println("You clicked with RPM");
                //ship.setRotate(getRotate()+90);
                double X=ship.getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
                double Y=ship.getBoundsInLocal().getWidth();
                System.out.println("Ship height = "+Y+" Ship width = "+X);
        }});


        ship.setOnMouseDragged(mouseEvent -> {
            if(move) {
                ship.setTranslateX(mouseEvent.getSceneX() + x);
                ship.setTranslateY(mouseEvent.getSceneY() + y);
            }
        });

        ship.setOnMouseReleased(mouseEvent -> {
            move = false;
            ship.setCursor(Cursor.HAND);
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Group getShip() {
        return ship;
    }

    public Pane getShipArena() {
        return shipArena;
    }

    public double getShipWidth() {
        return width;
    }

    public double getShipHeight() {
        return height;
    }
}
