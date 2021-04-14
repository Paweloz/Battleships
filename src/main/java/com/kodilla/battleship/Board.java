package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

/* Klasa tworząca plansze */

public class Board extends GridPane {
    private boolean enemy;
    private GridPane grid = new GridPane();
    private List<Cell> cellList = new LinkedList<>();


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
                cellList.add(cell);
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }

    public List<Cell> getCellList(){ return cellList; }

    public boolean isDroppedOn(){
        for(Cell cell : this.getCellList()){
            if(cell.isShipDroppedOn()) {
                return true;
            }
        }
        return false;
    }

    public Cell getCell(double x, double y){
        Cell cellForReturn = new Cell(0.1,0.1,this);
        for(Cell cell : this.getCellList()){
            if(cell.getCellX()==x && cell.getCellY()==y){
                cellForReturn = cell;
            }
        }
        return cellForReturn;
    }

    public boolean isEnemy() {
        return enemy;
    }
}
