package com.kodilla.battleship;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* Klasa odpowiadająca za działanie poszczególnych komórek planszy.
   Odpowiada za ich tworzenie i reagowanie na umieszenie statku */

public class Cell extends Rectangle {

    public double x, y;
    private Point2D point2D;
    public Ship ship = null;
    public boolean hasShip;
    public boolean shipDroppedOn;
    public boolean avaliable = true;
    public boolean wasShot = false;

    private final Board board;

    public Cell(double x, double y, Board board) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.board = board;
        this.point2D = new Point2D(x,y);
        setFill(Color.LIGHTBLUE);
        this.setOnMouseClicked(event -> System.out.println("Komórka "+x +" "+y));
    }


    public Point2D getCell(){
        return point2D;
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