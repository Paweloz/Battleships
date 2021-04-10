package com.kodilla.battleship;


import javafx.scene.paint.Color;
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
        this.setOnMouseClicked(event -> {
            double X = this.getCellX();
            double Y = this.getCellY();
            System.out.println("Position x "+X+" and y "+Y+" and board belongs to enemy : " + board.isEnemy());
        });
        this.setOnMouseDragReleased(event ->
        {
            System.out.println("Najechales statkiem na plansze");
        });
        this.setOnMousePressed(event -> System.out.println("Nacisnąłeś plansze"));
        this.setOnMouseEntered(event -> System.out.println("\"Najechales statkiem na plansze\""));
    }


    public int getCellX() {
        return x;
    }

    public int getCellY() {
        return y;
    }
}