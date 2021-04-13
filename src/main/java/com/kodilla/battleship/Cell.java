package com.kodilla.battleship;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* Klasa odpowiadająca za działanie poszczególnych komórek planszy.
   Odpowiada za ich tworzenie i reagowanie na umieszenie statku */

public class Cell extends Rectangle {

    public int x, y;
    public Ship ship = null;
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
            this.setFill(Color.RED);
            System.out.println("Drag dropped");
        });
    }


    public int getCellX() {
        return x;
    }

    public int getCellY() {
        return y;
    }
}