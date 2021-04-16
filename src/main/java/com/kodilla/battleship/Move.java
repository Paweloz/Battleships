package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.LinkedList;
import java.util.List;

/* Klasa udostępniajaca funkcjonalność ruchu dla statków oraz reakcji dla planszy */
public class Move {
    private double draggingX;
    private double draggingY;
    private Ship ship;
    private Group shipGroup;
    private Board board;
    List<Cell> cellsWithShip = new LinkedList<>();
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


        shipGroup.setOnDragDetected(event -> shipGroup.startFullDrag());


        shipGroup.setOnMouseDragged(event -> {
            shipGroup.getParent().toFront();
            shipGroup.setTranslateX(event.getSceneX() + draggingX);
            shipGroup.setTranslateY(event.getSceneY() + draggingY);
            event.setDragDetect(false);
        });
/////////////////////////////////////////////////////////////////////////////Tutaj zakoduj poprawne umieszczanie statków
        shipGroup.setOnMouseReleased(event -> {
            shipGroup.setMouseTransparent(false);
            try {
                placeShip(ship.getPickedRectangle(), ship.getShipTotalX(),ship.getShipTotalY(),board ,ship.isVertical());
            }catch (NullPointerException e){
                System.out.println();
            }
            setNeighborhood();

        });
    }

    public void setBoardReaction(){

        for (Cell cell : board.getCellList()){
            cell.setOnMouseDragReleased(event -> cell.setShipDroppedOn(true));
        }
    }

    // Metoda sprawdzajaca możliwość upuszczenia statku na planszy
    public void placeShip(int pickedRectangle, double shipTotalX, double shipTotalY, Board board, boolean vertical){
        Cell droppedCell = null;
        List<Cell> cellList = new LinkedList<>();
        boolean canPlaceShip = true;

            // Przeszukuje plansze w poszukiwaniu komórki na którą spadł statek
            // Pobiera ją, a następnie zeruje jej parametr,
            // aby zawsze była tylko 1 komórka na którą spadł w danej chwili statek.
            for (Cell cell : board.getCellList()) {
                if (cell.isShipDroppedOn()) {
                    droppedCell = cell;
                    cell.setShipDroppedOn(false);
                }
            }

            //Umieszczanie statku w poziomie
            if (!vertical) {
                double requiredOnTheLeft = pickedRectangle - 1;
                double requiredOnTheRight = shipTotalX/20 - pickedRectangle;

                if (droppedCell.isAvaliable()) {
                    for (Cell cell :  board.getCellList()){
                        if (cell.equals(droppedCell)) {
                            cellList.add(cell);
                        }
                    }

                    //Zaznaczanie wymaganych pól na lewo od miejsca upuszczcenia statu
                    for (double i = droppedCell.getCellX() - 1; i >= (droppedCell.getCellX() - requiredOnTheLeft); i--) {
                        for (Cell cell : board.getCellList()) {
                            if (cell.getCellX() == i && cell.getCellY() == droppedCell.getCellY()) {
                                if(cell.isAvaliable()) {
                                    cellList.add(cell);
                                }else{
                                    canPlaceShip = false;
                                }
                            }
                        }
                        // Zaznaczanie wymaganych pól na prawo od miejsca upuszczenia statku
                    }
                    for (double j = droppedCell.getCellX(); j <= (droppedCell.getCellX() + requiredOnTheRight); j++) {
                        for (Cell cell : board.getCellList()) {
                            if (cell.getCellX() == j && cell.getCellY() == droppedCell.getCellY()) {
                                if(cell.isAvaliable()) {
                                    cellList.add(cell);
                                }else{
                                    canPlaceShip = false;

                                }
                            }
                        }
                    }
                }
                //Tutaj to samo sprawdzenie tylko dla statku w pionie
            } else {
                double requiredOnTheTop = pickedRectangle - 1;
                double requiredOnTheBottom = shipTotalY/20 - pickedRectangle;

                if (droppedCell.isAvaliable()) {
                    for(Cell cell : board.getCellList()){
                        if (cell.equals(droppedCell)) {
                            cellList.add(cell);
                        }
                    }

                    //Zaznaczanie wymaganych w górę od miejsca upuszczcenia statu
                    for (double i = droppedCell.getCellY()-1; i >= (droppedCell.getCellY() - requiredOnTheTop); i--) {
                        for (Cell cell : board.getCellList()) {
                            if (cell.getCellY() == i && cell.getCellX() == droppedCell.getCellX()) {
                                if (cell.isAvaliable()) {
                                    cellList.add(cell);
                                }else{
                                    canPlaceShip = false;

                                }
                            }
                        }
                        // Zaznaczanie wymaganych w dół od miejsca upuszczenia statku
                    }
                    for (double j = droppedCell.getCellY(); j <= (droppedCell.getCellY() + requiredOnTheBottom); j++) {
                        for (Cell cell : board.getCellList()) {
                            if (cell.getCellY() == j && cell.getCellX() == droppedCell.getCellX()) {
                                if(cell.isAvaliable()) {
                                    cellList.add(cell);
                                }
                            }
                        }
                    }
                }
            }
            //TODO Sprawdzenie wartosci granicznych planszy
        //Ilość komórek w liście dla statku, musi być równa rozmiarowi statku
        //Sprawdza, czy żadne z potencajlnych pól statku nie zwróciło false, a następenie umieszcza statek na planszy
            if(canPlaceShip){
                System.out.println(cellList.size()+" "+ship.getType());
                if(cellList.size()-1 == ship.getType()) {
                    for (Cell cell : board.getCellList()) {
                        for (Cell cell1 : cellList) {
                            if (cell.getCellX() == cell1.getCellX() && cell.getCellY() == cell1.getCellY()) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                        }
                    }shipGroup.setVisible(false);
                }else{
                    shipGroup.setVisible(true);
                }
            }

    }

    // Ustalanie sąsiedzctwa dla statków
    public void setNeighborhood(){

        for (Cell cell : cellsWithShip){
            Cell temp = new Cell(cell.getCellX(),cell.getCellY());
            for(Cell cell1 : board.getCellList()){
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
}
