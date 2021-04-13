package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/* Klasa tworząca plansze

 */

public class Board extends GridPane {
    boolean enemy;

    public GridPane grid = new GridPane();

    public Board(boolean enemy){
        this.enemy = enemy;
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10,10,10,30));
        grid.setHgap(1);
        grid.setVgap(1);
        grid.toBack();
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Cell cell = new Cell(i,j,this);
                cell.setFill(Color.LIGHTBLUE);
                grid.add(cell, i, j);
            }
        }
    }
    public GridPane getGrid() {
        return grid;
    }

    public boolean isEnemy() {
        return enemy;
    }
}
