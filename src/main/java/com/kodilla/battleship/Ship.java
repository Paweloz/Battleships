package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.LinkedList;
import java.util.List;

/* Klasa odpowiadająca za tworzenie statku
*/

public class Ship extends Rectangle {
    private final int type;
    private double draggingX;
    private double draggingY;
    private double width;
    private double height;
    private Group ship = new Group();
    private List<Rectangle> shipInList = new LinkedList<>();
    private int pickedRectangle;
    private boolean vertical = false;
    private double shipTotalX;
    private double shipTotalY;



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
            shipInList.add(r);
            r.setOnMousePressed(event -> {
                if(event.getButton()==MouseButton.PRIMARY) {
                    r.setId("x");
                }
            });
            shipTotalY = ship.getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
            shipTotalX = ship.getBoundsInLocal().getWidth();
            System.out.println(shipTotalX+" "+shipTotalY);
        }
        //Obsługa eventów press drag release dla grupy kwadratów tworzących statek
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ship.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ship.setMouseTransparent(true);
                event.setDragDetect(true);
                draggingX = ship.getTranslateX() - event.getSceneX();
                draggingY = ship.getTranslateY() - event.getSceneY();

            } else {
                //ship.setRotate(ship.getRotate()+90);
                ship.getTransforms().add(new Rotate(90));
                shipTotalX = ship.getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
                shipTotalY = ship.getBoundsInLocal().getWidth();
                System.out.println(shipTotalX+" "+shipTotalY);
                vertical = true;
        }});


        ship.setOnDragDetected(event -> {
            ship.startFullDrag();
        });


        ship.setOnMouseDragged(event -> {
            ship.getParent().toFront();
            ship.setTranslateX(event.getSceneX() + draggingX);
            ship.setTranslateY(event.getSceneY() + draggingY);
            event.setDragDetect(false);
        });

        ship.setOnMouseReleased(event -> {
            ship.setMouseTransparent(false);
            for (int i=0; i<shipInList.size();i++){
                Rectangle r = shipInList.get(i);
                if(r.getId()=="x"){
                    pickedRectangle = i+1;
                    System.out.println("Znaleziono kratke w innym kolorze w pozycji : "+pickedRectangle);
                }
            }

        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Group getShip() {
        return ship;
    }

    public boolean isVertical() {
        return vertical;
    }

    public double getShipWidth() {
        return width;
    }

    public double getShipHeight() {
        return height;
    }


}
