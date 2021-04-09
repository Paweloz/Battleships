package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Board extends GridPane {
    boolean enemy;

    public GridPane grid = new GridPane();

    public Board(boolean enemy){
        this.enemy = enemy;
        grid.setVisible(true);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10,10,10,30));
        grid.setHgap(1);
        grid.setVgap(1);
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Cell cell = new Cell(i,j,this);
                cell.setFill(Color.LIGHTBLUE);
                cell.setOnMouseClicked(event -> {
                    double x = cell.getCellX();
                    double y = cell.getCellY();
                    System.out.println("Position x "+x+" and y "+y+" and board belongs to enemy : " + enemy);
                });
                grid.add(cell, i, j);
            }
        }
    }
    public GridPane getGrid() {
        return grid;
    }
}
