package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
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
    private Board board;



// Konstruktor przyjmuje wielkość statku oraz jego kolor,
// a następnie tworzy odpowiednią ilość kwadratów o zadanym rozmiarze i dodaje je do grupy
    public Ship(double width, double height, Paint fill, int type, Board board) {
        super(width, height, fill);
        this.width = width;
        this.height = height;
        this.type = type;
        this.board = board;
        this.shipTotalX = width * type;
        this.shipTotalY = height;

        for (int i = 0; i < type; i++) {
            Rectangle r = new Rectangle(width, height, fill);
            r.setX(i * width + 1);
            r.setStroke(Color.BLUE);
            ship.getChildren().add(r);
            shipInList.add(r);
            r.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    r.setId("x");
                }
            });
            r.setOnMouseReleased(event -> {
                r.setId(null);
            });
        }
    }


    public Group getShip() {
        return ship;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public void setPickedRectangle(int pickedRectangle) {
        this.pickedRectangle = pickedRectangle;
    }

    public int getType() {
        return type;
    }

    public void setShipTotalX(double shipTotalX) {
        this.shipTotalX = shipTotalX;
    }

    public void setShipTotalY(double shipTotalY) {
        this.shipTotalY = shipTotalY;
    }

    public List<Rectangle> getShipInList() {
        return shipInList;
    }

    public double getShipWidth() {
        return width;
    }

    public int getPickedRectangle() {
        return pickedRectangle;
    }

    public double getShipTotalX() {
        return shipTotalX;
    }

    public double getShipTotalY() {
        return shipTotalY;
    }

    public double getShipHeight() {
        return height;
    }


}
