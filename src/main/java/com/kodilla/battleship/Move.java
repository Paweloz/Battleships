package com.kodilla.battleship;

import javafx.geometry.Point2D;
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
            try {
                canPlaceShip(ship.getPickedRectangle(), ship.getShipTotalX(),ship.getShipTotalY(),board ,ship.isVertical());
            }catch (NullPointerException e){
            }
            setNeighborhood();

        });
    }

    public void setBoardReaction(){

        for (Cell cell : board.getCellList()){
            cell.setOnMouseDragReleased(event ->{
                cell.setShipDroppedOn(true);
            });
        }
    }


    // Metoda sprawdzajaca możliwość upuszczenia statku na planszy
    public List<Cell> canPlaceShip(int pickedRectangle, double shipTotalX, double shipTotalY, Board board, boolean vertical){
        List<Cell> cellForShip = new LinkedList<>(); // serio potrzebne ?
        Cell droppedCell = null;
        //Ilość komórek zajętych przez statek w osiX i Y
        double shipCellsX = shipTotalX/20;
        double shipCellsY = shipTotalY/20;

        // Przeszukuje plansze w poszukiwaniu komórki na którą spadł statek
        // Pobiera ją, a następnie zeruje jej parametr,
        // aby zawsze była tylko 1 komórka na którą spadł w danej chwili statek.
        for(Cell cell : board.getCellList()){
            int count =0;
            if(cell.isShipDroppedOn()){
                count += 1;
                droppedCell = cell;
                cell.setShipDroppedOn(false);
                //System.out.println("Ilość komórek na które został upuszczony statek :"+count);
            }
        }

        // Przeszukuje plansze w poszukiwaniu komórek na których jest już inny statek
        for(Cell cell : board.getCellList()){
            if(cell.containsShip()){
                cellsWithShip.add(cell);
            }
        }


        //System.out.println("Statek umieszczony w komórce :"+ droppedCell.getCellX()+" "+droppedCell.getCellY());
        //System.out.println("Długość statku to :"+ shipCellsX+"w poziomie, oraz "+shipCellsY+" w pionie");

        //TODO Ustalanie sąsiedztwa dla statku
        //TODO Sprawdzenie legalnośći położenia statku
        //Umieszczanie statku w poziomie
        if(!vertical){
            double requiredOnTheLeft = pickedRectangle - 1;
            double requiredOnTheRight = shipCellsX - pickedRectangle;

            if(droppedCell.isAvaliable()){
                //Zaznaczanie wymaganych pól na lewo od miejsca upuszczcenia statu
                for (double i = droppedCell.getCellX()-1; i>=(droppedCell.getCellX()-requiredOnTheLeft);i--){
                    for(Cell cell : board.getCellList()) {
                            if (cell.equals(droppedCell)) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                            if (cell.getCellX() == i && cell.getCellY() == droppedCell.getCellY()) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                    }
                    // Zaznaczanie wymaganych pól na prawo od miejsca upuszczenia statku
                }for (double j = droppedCell.getCellX(); j<=(droppedCell.getCellX()+requiredOnTheRight);j++){
                    for(Cell cell : board.getCellList()){
                            if (cell.getCellX() == j && cell.getCellY() == droppedCell.getCellY()) {
                                cell.setFill(Color.DARKBLUE);
                                cell.setAvaliable(false);
                                cell.setHasShip(true);
                                cellsWithShip.add(cell);
                            }
                    }
                }
                shipGroup.setVisible(false);
            }


        //Tutaj to samo sprawdzenie tylko dla statku w pionie
        }else {
            double requiredOnTheTop = pickedRectangle - 1;
            double requiredOnTheBottom = shipCellsY - pickedRectangle;

            if(droppedCell.isAvaliable()){
                //Zaznaczanie wymaganych w górę od miejsca upuszczcenia statu
                for (double i = droppedCell.getCellY(); i>=(droppedCell.getCellY()-requiredOnTheTop);i--){
                    for(Cell cell : board.getCellList()){
                        if(cell.equals(droppedCell)){
                            cell.setFill(Color.DARKBLUE);
                            cell.setAvaliable(false);
                            cell.setHasShip(true);
                            cellsWithShip.add(cell);
                        }
                        if(cell.getCellY()==i && cell.getCellX()==droppedCell.getCellX()){
                            cell.setFill(Color.DARKBLUE);
                            cell.setAvaliable(false);
                            cell.setHasShip(true);
                            cellsWithShip.add(cell);
                        }
                    }
                    // Zaznaczanie wymaganych w dół od miejsca upuszczenia statku
                }for (double j = droppedCell.getCellY(); j<=(droppedCell.getCellY()+requiredOnTheBottom);j++){
                    for(Cell cell : board.getCellList()){
                        if(cell.getCellY()==j && cell.getCellX()==droppedCell.getCellX()){
                            cell.setFill(Color.DARKBLUE);
                            cell.setAvaliable(false);
                            cell.setHasShip(true);
                            cellsWithShip.add(cell);
                        }
                    }
                }
                shipGroup.setVisible(false);

            }
        }
        return cellForShip;
    }

    // Ustalanie sąsiedzctwa dla statków
    public void setNeighborhood(){

        for (Cell cell : cellsWithShip){
            Cell temp = new Cell(cell.getCellX(),cell.getCellY());
            for(Cell cell1 : board.getCellList()){
                if(cell1.getCellX()==temp.getCellX() && cell1.getCellY() == temp.getCellY()-1){
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if(cell1.getCellX()==temp.getCellX() && cell1.getCellY() == temp.getCellY()+1){
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
