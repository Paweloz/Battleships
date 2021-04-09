package com.kodilla.battleship;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public int x, y;
    public Ship ship = null;
    public boolean wasShot = false;

    private Board board;

    public Cell(int x, int y, Board board) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTBLUE);
    }


    public int getCellX() {
        return x;
    }

    public int getCellY() {
        return y;
    }
}