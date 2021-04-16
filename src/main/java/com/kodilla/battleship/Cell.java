package com.kodilla.battleship;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* Klasa odpowiadająca za działanie poszczególnych komórek planszy.
   Odpowiada za ich tworzenie i reagowanie na umieszenie statku */

public class Cell extends Rectangle {

    public double x, y;
    public Ship ship = null;
    public boolean hasShip;
    public boolean shipDroppedOn;
    public boolean avaliable = true;
    public boolean wasShot = false;

    private Board board= null;

    public Cell(double x, double y, Board board) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTBLUE);
        this.setOnMouseClicked(event -> System.out.println("Komórka X :"+x +" Y: "+y));
    }

    public Cell(double x, double y){
        super(20,20);
        this.x = x;
        this.y = y;
    }


    public double getCellX() {
        return x;
    }

    public boolean isShipDroppedOn() {
        return shipDroppedOn;
    }

    public void setShipDroppedOn(boolean shipDroppedOn) {
        this.shipDroppedOn = shipDroppedOn;
    }

    public boolean containsShip() {
        return hasShip;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public double getCellY() {
        return y;
    }
}