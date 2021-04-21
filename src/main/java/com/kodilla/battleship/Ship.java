package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/* Klasa odpowiadająca za tworzenie statku */

public class Ship extends Rectangle {
    private final int type;
    private final double width;
    private final double height;
    private final Group ship = new Group();
    private final List<Rectangle> shipInList = new ArrayList<>();
    private int pickedRectangle;
    private boolean vertical = false;
    private double shipTotalX;
    private double shipTotalY;

    public Ship(double width, double height, Paint fill, int type) {
        super(width, height, fill);
        this.width = width;
        this.height = height;
        this.type = type;
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
                if (event.getButton() == MouseButton.PRIMARY) {
                    r.setId(null);
                }
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
