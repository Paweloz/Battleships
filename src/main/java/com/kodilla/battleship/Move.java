package com.kodilla.battleship;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.LinkedList;
import java.util.List;

public class Move {
    private double draggingX;
    private double draggingY;
    private Ship ship;
    private Group shipGroup;
    private Board board;
    private int x;
    private int y;


    public Move(Ship ship,Board board){
        this.ship=ship;
        this.shipGroup=ship.getShip();
        this.board = board;
    }


    public void setShipMoves() {
        shipGroup.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shipGroup.setMouseTransparent(true);
                event.setDragDetect(true);
                for (int i = 0; i < ship.getShipInList().size(); i++) {
                    Rectangle r = ship.getShipInList().get(i);
                    if (r.getId() == "x") {
                        ship.setPickedRectangle(i+1);
                    }
                }
                draggingX = shipGroup.getTranslateX() - event.getSceneX();
                draggingY = shipGroup.getTranslateY() - event.getSceneY();
            } else {
                shipGroup.getTransforms().add(new Rotate(90));
                ship.setShipTotalX(ship.getShipHeight());
                ship.setShipTotalY(ship.getShipWidth()*ship.getType());
                ship.setVertical(true);
            }
        });


        shipGroup.setOnDragDetected(event -> {
            shipGroup.startFullDrag();
        });


        shipGroup.setOnMouseDragged(event -> {
            shipGroup.getParent().toFront();
            shipGroup.setTranslateX(event.getSceneX() + draggingX);
            shipGroup.setTranslateY(event.getSceneY() + draggingY);
            event.setDragDetect(false);
        });
/////////////////////////////////////////////////////////////////////////////Tutaj zakoduj poprawne umieszczanie statków
        shipGroup.setOnMouseReleased(event -> {
            shipGroup.setMouseTransparent(false);
            canPlaceShip(ship.getPickedRectangle(), ship.getShipTotalX(),ship.getShipTotalY(),board ,ship.isVertical());
            ship.setPickedRectangle(0);
        });
    }

    public void setBoardReaction(){

        for (Cell cell : board.getCellList()){
            cell.setOnMouseDragReleased(event ->{
                cell.setFill(Color.RED);
                cell.setShipDroppedOn(true);
                cell.setAvaliable(false);
            });
        }
    }


    // Metoda sprawdzajaca możliwość upuszczenia statku na planszy
    public List<Cell> canPlaceShip(int pickedRectangle, double shipTotalX, double shipTotalY, Board board, boolean vertical){
        List<Cell> cellForShip = new LinkedList<>(); // serio potrzebne ?
        List<Cell> cellsWithShip = new LinkedList<>();
        Point2D droppedOnCell = new Point2D(0,0);
        Cell droppedCell = null;
        double shipCellsX = shipTotalX/20;
        double shipCellsY = shipTotalY/20;


        // Przeszukuje plansze w poszukiwaniu komórki na którą spadł statek
        for(Cell cell : board.getCellList()){
            if(cell.isShipDroppedOn()){
                droppedOnCell = cell.getCell();
                droppedCell = cell;
                cell.setShipDroppedOn(false);
                cell.setFill(Color.LIGHTBLUE);
            }
        }
        // Przeszukuje plansze w poszukiwaniu komórek na których jest już inny statek
        for(Cell cell : board.getCellList()){
            if(cell.containsShip()){
                cell.setAvaliable(false);
                cellsWithShip.add(cell);
            }
        }
        System.out.println("Statek umieszczony w komórce :"+ droppedOnCell);
        System.out.println("Długość statku to :"+ shipCellsX+"w poziomie, oraz "+shipCellsY+" w pionie");

        //
        if(!vertical){
            for(double i=0;i<shipCellsX;i++) {
                if (board.getCell(droppedCell.getCellX()+i,droppedCell.getCellY()).isAvaliable()){
                    for (double j=0; j<shipCellsY;j++){
                        if (board.getCell(droppedCell.getCellX(), droppedCell.getCellY()+j).isAvaliable()){

                        }
                    }
                }

            }


        }else {
            //Tutaj to samo sprawdzenie tylko dla statku w pionie
        }


        return cellForShip;
    }



}
