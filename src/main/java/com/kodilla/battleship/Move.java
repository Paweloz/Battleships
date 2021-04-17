package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* Klasa udostępniajaca funkcjonalność ruchu dla statków oraz reakcji dla planszy */
public class Move {
    private double draggingX;
    private double draggingY;
    private Ship ship;
    private Group shipGroup;
    private static Board board;
    List<Cell> cellsWithShip = new LinkedList<>();


    public Move(Ship ship,Board board){
        this.ship=ship;
        this.shipGroup=ship.getShip();
        this.board = board;
    }


    public void setShipMoves() {
        shipGroup.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shipGroup.setMouseTransparent(true);
                //ship.setVertical(false);
                event.setDragDetect(true);
                for (int i = 0; i < ship.getShipInList().size(); i++) {
                    Rectangle r = ship.getShipInList().get(i);
                    if (r.getId() == "x") {
                        ship.setPickedRectangle(i+1);
                    }
                }
                draggingX = shipGroup.getTranslateX() - event.getSceneX();
                draggingY = shipGroup.getTranslateY() - event.getSceneY();
            } else if(event.getButton() == MouseButton.SECONDARY){
                ship.setVertical(true);
                draggingX = shipGroup.getTranslateX() - event.getSceneX();
                draggingY = shipGroup.getTranslateY() - event.getSceneY();
                shipGroup.setRotate(shipGroup.getRotate()+90);
                ship.setShipTotalX(ship.getShipHeight());
                ship.setShipTotalY(ship.getShipWidth()*ship.getType());
            }
        });


        shipGroup.setOnDragDetected(event -> shipGroup.startFullDrag());


        shipGroup.setOnMouseDragged(event -> {
            shipGroup.getParent().toFront();
            shipGroup.setTranslateX(event.getSceneX() + draggingX);
            shipGroup.setTranslateY(event.getSceneY() + draggingY);
            event.setDragDetect(false);
        });

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
    public void placeShip(int pickedRectangle, double shipTotalX, double shipTotalY, @NotNull Board board, boolean vertical){
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

                    //Zaznaczanie wymaganych pól na lewo od miejsca upuszczcenia statu
                    for (double i = droppedCell.getCellX(); i >= (droppedCell.getCellX() - requiredOnTheLeft); i--) {
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
                    for (double j = droppedCell.getCellX()+1; j <= (droppedCell.getCellX() + requiredOnTheRight); j++) {
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
                            cell.setAvaliable(false);
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

        //Ilość komórek w liście dla statku, musi być równa rozmiarowi statku
        //Sprawdza, czy żadne z potencajlnych pól statku nie zwróciło false, a następenie umieszcza statek na planszy
            if(canPlaceShip){
                if(cellList.size() == ship.getType()) {
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
                    board.setShipsPlaced(board.getShipsPlaced()+1);
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
    public static void startGame(Board enemyBoard, Board playerBoard){
        int cellsWithShip =0;
        if(playerBoard.getShipsPlaced() == 5){
            System.out.println("Start game");
            for (Cell cell : enemyBoard.getCellList()){
                cell.setOnMouseClicked(event -> {
                    if(enemyBoard.isEnemy() && cell.containsShip() && !cell.isWasShot()){
                        cell.setFill(Color.RED);
                        cell.setHasShip(false);
                        cell.setWasShot(true);
                    } else if (!cell.isWasShot()) {
                        cell.setFill(Color.BLACK);
                        cell.setWasShot(true);
                    }

                        Random generateX = new Random();
                        Random generateY = new Random();
                        double enemyShootX = generateX.nextInt(10);
                        double enemyShootY = generateY.nextInt(10);
                        for (Cell cell1 : playerBoard.getCellList()){
                            if(cell1.getCellX() == enemyShootX && cell1.getCellY() == enemyShootY){
                                if(cell1.containsShip() && !cell1.isWasShot()){
                                    cell1.setWasShot(true);
                                    cell1.setHasShip(false);
                                    cell1.setFill(Color.RED);
                                }else if (!cell1.containsShip() && !cell1.isWasShot() ){
                                    cell1.setWasShot(true);
                                    cell1.setFill(Color.BLACK);
                                }else {
                                    System.out.println("Tu już cpu strzelił, pasuje, zeby strzelił, gdzie indziej");
                                }
                            }
                        }
                });
            }




        }else {
            System.out.println("Nie rozmieściłeś jeszcze wszystkich statków");
        }
    }
}
