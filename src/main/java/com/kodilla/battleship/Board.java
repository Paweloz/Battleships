package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* Klasa tworząca plansze */

public class Board extends GridPane {
    private boolean enemy;
    private GridPane grid = new GridPane();
    private List<Cell> cellList = new LinkedList<>();
    private Text[] letters = {new Text("   "), new Text("  A"),new Text("  B"),new Text("  C"),new Text("  D"),new Text("  E"),new Text("  F"),
            new Text("  G"),new Text("  H"),new Text("  I"),new Text("  J")};
    private Text[] numbers = {new Text("   "), new Text(" 1"),new Text(" 2"),new Text(" 3"),new Text(" 4"),new Text(" 5"),new Text(" 6"),
            new Text(" 7"),new Text(" 8"),new Text(" 9"),new Text(" 10")};
    private List<Cell> cellsWithShip = new LinkedList<>();


    public Board(boolean enemy){
        this.enemy = enemy;
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10,10,10,30));
        grid.setHgap(1);
        grid.setVgap(1);
        grid.toBack();

        for(int i=0;i<10;i++){
            grid.add(letters[i],i,0);
            grid.add(numbers[i],0,i);
            for(int j=0;j<10;j++){
                Cell cell = new Cell(i,j,this);
                cell.setFill(Color.LIGHTBLUE);
                grid.add(cell, i+1, j+1);
                cellList.add(cell);
            }
        }
        grid.add(letters[10],10,0);
        grid.add(numbers[10],0,10);

        if(enemy){
            setEnemyShip( 100,20, false);
            setNeighborhood();
            setEnemyShip(20,80, true);
            setNeighborhood();
            setEnemyShip(20,60,true);
            setNeighborhood();
            setEnemyShip(60,20, false);
            setNeighborhood();
            setEnemyShip(40,20,false);
            setNeighborhood();
        }
    }

    // Metoda umieszcza statek przeciwnika zachowując zasady gry
    public void setEnemyShip(double shipTotalX, double shipTotalY, boolean vertical){
        List<Cell> cellsForShip = new LinkedList<>();
        boolean canPlaceShip = true;
        boolean shipDone = false;

        while (!shipDone) {
            Random generateX = new Random();
            Random generateY = new Random();
            double droppedCellX = generateX.nextInt(10);
            double droppedCellY = generateY.nextInt(10);
            Cell droppedCell = new Cell(droppedCellX,droppedCellY);
            droppedCell.setAvaliable(true);

            //Umieszczanie statku w poziomie
            if (!vertical) {
                //Zakładając, że komputer zaczyna ukłądanie statku zawsze od 1ego kwadratu
                double requiredOnTheRight = shipTotalX/20 - 1;

                    // Zaznaczanie wymaganych pól na prawo od miejsca upuszczenia statku
                for (double j = droppedCell.getCellX(); j <= (droppedCell.getCellX() + requiredOnTheRight); j++) {
                    for (Cell cell : this.getCellList()) {
                        if (cell.getCellX() == j && cell.getCellY() == droppedCell.getCellY()) {
                            if (cell.isAvaliable()) {
                                cell.setAvaliable(false);
                                cellsForShip.add(cell);
                            } else {
                                canPlaceShip = false;
                            }
                        }
                    }
                }
            } else {
                // Zakładając, że komputer zaczyna ukłądanie statku zawsze od 1ego kwadratu
                double requiredOnTheBottom = shipTotalY / 20 - 1;

                // Zaznaczanie wymaganych w dół od miejsca upuszczenia statku
                for (double j = droppedCell.getCellY(); j <= (droppedCell.getCellY() + requiredOnTheBottom); j++) {
                    for (Cell cell : this.getCellList()) {
                        if (cell.getCellY() == j && cell.getCellX() == droppedCell.getCellX()) {
                            if (cell.isAvaliable()) {
                                cell.setAvaliable(false);
                                cellsForShip.add(cell);
                            } else {
                                canPlaceShip = false;
                            }
                        }
                    }
                }
            }
            //Ilość komórek w liście dla statku, musi być równa rozmiarowi statku
            //Sprawdza, czy żadne z potencajlnych pól statku nie zwróciło false, a następenie umieszcza statek na planszy
            if (canPlaceShip) {
                System.out.println("Ilość komórek " + (cellsForShip.size()) + " total X " + (int) shipTotalX / 20 + " total Y "+ (int) shipTotalY/20);
                if (cellsForShip.size()== shipTotalX/20 && !vertical) {
                    for (Cell cell : this.getCellList()) {
                        for (Cell cell1 : cellsForShip) {
                            if (cell.getCellX() == cell1.getCellX() && cell.getCellY() == cell1.getCellY()) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                        }
                    }
                    shipDone = true;
                }else if(cellsForShip.size() == shipTotalY/20 && vertical){
                    for (Cell cell : this.getCellList()) {
                        for (Cell cell1 : cellsForShip) {
                            if (cell.getCellX() == cell1.getCellX() && cell.getCellY() == cell1.getCellY()) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                        }
                    }
                    shipDone = true;
                }
            }
            canPlaceShip = true;
            cellsForShip.clear();
        }
    }

    // Ustalanie sąsiedzctwa dla statków
    public void setNeighborhood(){

        for (Cell cell : cellsWithShip){
            Cell temp = new Cell(cell.getCellX(),cell.getCellY());
            for(Cell cell1 : this.getCellList()){
                if(cell1.getCellX()==temp.getCellX() && cell1.getCellY() == temp.getCellY()-1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX() && cell1.getCellY() == temp.getCellY()+1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX()-1 && cell1.getCellY()==temp.getCellY() && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX()+1 && cell1.getCellY()==temp.getCellY() && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX()+1 && cell1.getCellY()==temp.getCellY()+1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX()+1 && cell1.getCellY()==temp.getCellY()-1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }if(cell1.getCellX()==temp.getCellX()-1 && cell1.getCellY()==temp.getCellY()+1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX()-1 && cell1.getCellY()==temp.getCellY()-1 && !cell1.containsShip()){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
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
