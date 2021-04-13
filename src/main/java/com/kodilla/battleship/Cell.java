package com.kodilla.battleship;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* Klasa odpowiadająca za działanie poszczególnych komórek planszy.
   Odpowiada za ich tworzenie i reagowanie na umieszenie statku */

public class Cell extends Rectangle {

    public int x, y;
    public Ship ship = null;
    public boolean hasShip;
    public boolean shipDroppedOn;
    public boolean wasShot = false;

    private final Board board;

    public Cell(int x, int y, Board board) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTBLUE);


        this.setOnMouseDragEntered(event -> {
            System.out.println("Dragging detected");
        });

        this.setOnMouseDragReleased(event -> {
            //board.canPlaceShip();
            this.setFill(Color.RED);
            shipDroppedOn = true;
            System.out.println("Drag dropped");
        });
    }


    public int getCellX() {
        return x;
    }

    public boolean isShipDroppedOn() {
        return shipDroppedOn;
    }

    public int getCellY() {
        return y;
    }
}