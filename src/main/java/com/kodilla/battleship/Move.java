package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* Klasa udostępniajaca funkcjonalność ruchu dla statków oraz reakcji dla planszy */
public class Move {
    private static GameWindow gameWindow;
    private double draggingX;
    private double draggingY;
    private final Ship ship;
    private final Group shipGroup;
    private static Board board;
    List<Cell> cellsWithShip = new ArrayList<>();
    private static boolean enemyMissed = false;
    private static boolean notTheSameTarget = false;
    private static double enemyShootY;
    private static double enemyShootX;
    private static double previousX;
    private static double previousY;



    public Move(Ship ship, Board board) {
        this.ship = ship;
        this.shipGroup = ship.getShip();
        this.board = board;
    }


    public void setShipMoves() {
        shipGroup.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Text rotationInfo = new Text("If you want to rotate your ship,\n do it before dropping");
                rotationInfo.setFont(new Font(20));
                gameWindow.setComunicates(rotationInfo);
                shipGroup.setMouseTransparent(true);
                event.setDragDetect(true);
                for (int i = 0; i < ship.getShipInList().size(); i++) {
                    Rectangle r = ship.getShipInList().get(i);
                    if (Objects.equals(r.getId(), "x")) {
                        ship.setPickedRectangle(i + 1);
                    }
                }
                draggingX = shipGroup.getTranslateX() - event.getSceneX();
                draggingY = shipGroup.getTranslateY() - event.getSceneY();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                draggingX = shipGroup.getTranslateX() - event.getSceneX();
                draggingY = shipGroup.getTranslateY() - event.getSceneY();
                shipGroup.setRotate(shipGroup.getRotate() + 90);
                if(shipGroup.getRotate() == 90 || shipGroup.getRotate() == 270 || shipGroup.getRotate()%360==90 ||
                         shipGroup.getRotate()%360==270){
                    ship.setVertical(true);
                    ship.setShipTotalX(ship.getShipHeight());
                    ship.setShipTotalY(ship.getShipWidth() * ship.getType());

                }else {
                    ship.setVertical(false);
                    ship.setShipTotalX(ship.getShipWidth() * ship.getType());
                    ship.setShipTotalY(ship.getShipHeight());
                }
            }
        });


        shipGroup.setOnDragDetected(event -> {
            if (event.getButton()==MouseButton.PRIMARY){
                shipGroup.startFullDrag();
            }
        });


        shipGroup.setOnMouseDragged(event -> {
            if (event.getButton()==MouseButton.PRIMARY) {
                shipGroup.getParent().toFront();
                shipGroup.setTranslateX(event.getSceneX() + draggingX);
                shipGroup.setTranslateY(event.getSceneY() + draggingY);
                event.setDragDetect(false);
            }
        });

        shipGroup.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                shipGroup.setMouseTransparent(false);
                try {
                    placeShip(ship.getPickedRectangle(), ship.getShipTotalX(), ship.getShipTotalY(), board, ship.isVertical());
                } catch (NullPointerException e) {
                    System.out.println();
                }
                setNeighborhood();

            }
        });
    }

    public void setBoardReaction() {

        for (Cell cell : board.getCellList()) {
            cell.setOnMouseDragReleased(event -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    cell.setShipDroppedOn(true);
                }
            });
        }
    }


    // Metoda sprawdzajaca możliwość upuszczenia statku na planszy
    public void placeShip(int pickedRectangle, double shipTotalX, double shipTotalY, @NotNull Board board, boolean vertical) {
        Cell droppedCell = null;
        List<Cell> cellList = new ArrayList<>();
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
        System.out.println(droppedCell.getCellX()+" "+droppedCell.getCellY());
        // Zmiana kolejności liczenia pickedRectangle w zależoności od ilości obróceń statku
        if(shipGroup.getRotate() == 180 || shipGroup.getRotate()%360 == 180
                || shipGroup.getRotate() == 270 || shipGroup.getRotate()%360==270){
            if(pickedRectangle==ship.getType()){
                pickedRectangle = 1;
            }else if(pickedRectangle == ship.getType() -1){
                pickedRectangle = 2;

            }else if(pickedRectangle == ship.getType() -2){
                pickedRectangle = 3;

            }else if(pickedRectangle == ship.getType() -3){
                pickedRectangle = 4;

            }else if(pickedRectangle == ship.getType() -4){
                pickedRectangle = 5;

            }

        }
        System.out.println(pickedRectangle);
        //Umieszczanie statku w poziomie
        if (!vertical) {
            double requiredOnTheLeft = pickedRectangle - 1;
            double requiredOnTheRight = shipTotalX / 20 - pickedRectangle;


            if (droppedCell.isAvaliable()) {
                //Zaznaczanie wymaganych pól na lewo od miejsca upuszczcenia statu
                for (double i = droppedCell.getCellX(); i >= (droppedCell.getCellX() - requiredOnTheLeft); i--) {
                    for (Cell cell : board.getCellList()) {
                        if (cell.getCellX() == i && cell.getCellY() == droppedCell.getCellY()) {
                            if (cell.isAvaliable()) {
                                cellList.add(cell);
                            } else {
                                canPlaceShip = false;
                            }
                        }
                    }
                    // Zaznaczanie wymaganych pól na prawo od miejsca upuszczenia statku
                }
                for (double j = droppedCell.getCellX() + 1; j <= (droppedCell.getCellX() + requiredOnTheRight); j++) {
                    for (Cell cell : board.getCellList()) {
                        if (cell.getCellX() == j && cell.getCellY() == droppedCell.getCellY()) {
                            if (cell.isAvaliable()) {
                                cellList.add(cell);
                            } else {
                                canPlaceShip = false;
                            }
                        }
                    }
                }

            }

            //Tutaj to samo sprawdzenie tylko dla statku w pionie
        } else {
            double requiredOnTheTop = pickedRectangle - 1;
            double requiredOnTheBottom = shipTotalY / 20 - pickedRectangle;

            if (droppedCell.isAvaliable()) {
                for (Cell cell : board.getCellList()) {
                    if (cell.equals(droppedCell)) {
                        cellList.add(cell);
                        cell.setAvaliable(false);
                    }
                }

                //Zaznaczanie wymaganych w górę od miejsca upuszczcenia statu
                for (double i = droppedCell.getCellY() - 1; i >= (droppedCell.getCellY() - requiredOnTheTop); i--) {
                    for (Cell cell : board.getCellList()) {
                        if (cell.getCellY() == i && cell.getCellX() == droppedCell.getCellX()) {
                            if (cell.isAvaliable()) {
                                cellList.add(cell);
                            } else {
                                canPlaceShip = false;

                            }
                        }
                    }
                    // Zaznaczanie wymaganych w dół od miejsca upuszczenia statku
                }
                for (double j = droppedCell.getCellY(); j <= (droppedCell.getCellY() + requiredOnTheBottom); j++) {
                    for (Cell cell : board.getCellList()) {
                        if (cell.getCellY() == j && cell.getCellX() == droppedCell.getCellX()) {
                            if (cell.isAvaliable()) {
                                cellList.add(cell);
                            }
                        }
                    }
                }
            }
        }

        //Ilość komórek w liście dla statku, musi być równa rozmiarowi statku
        //Sprawdza, czy żadne z potencajlnych pól statku nie zwróciło false, a następenie umieszcza statek na planszy
        if (canPlaceShip) {
            if (cellList.size() == ship.getType()) {
                for (Cell cell : board.getCellList()) {
                    for (Cell cell1 : cellList) {
                        if (cell.getCellX() == cell1.getCellX() && cell.getCellY() == cell1.getCellY()) {
                            cell.setFill(Color.DARKBLUE);
                            cell.setAvaliable(false);
                            cell.setHasShip(true);
                            cellsWithShip.add(cell);
                        }
                    }
                }
                shipGroup.setVisible(false);
                board.setShipsPlaced(board.getShipsPlaced() + 1);
                Text shipPlaced = new Text("Ship placed succefully");
                shipPlaced.setFont(new Font(20));
                gameWindow.setComunicates(shipPlaced);
            } else {
                shipGroup.setVisible(true);
                Text wrongPlace = new Text("You cannot place your ship here");
                wrongPlace.setFont(new Font(20));
                gameWindow.setComunicates(wrongPlace);
            }
        }
    }

    // Ustalanie sąsiedzctwa dla statków
    public void setNeighborhood() {

        for (Cell cell : cellsWithShip) {
            Cell temp = new Cell(cell.getCellX(), cell.getCellY());
            for (Cell cell1 : board.getCellList()) {
                if (cell1.getCellX() == temp.getCellX() && cell1.getCellY() == temp.getCellY() - 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() && cell1.getCellY() == temp.getCellY() + 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() - 1 && cell1.getCellY() == temp.getCellY() && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() + 1 && cell1.getCellY() == temp.getCellY() && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() + 1 && cell1.getCellY() == temp.getCellY() + 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() + 1 && cell1.getCellY() == temp.getCellY() - 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() - 1 && cell1.getCellY() == temp.getCellY() + 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
                if (cell1.getCellX() == temp.getCellX() - 1 && cell1.getCellY() == temp.getCellY() - 1 && !cell1.containsShip()) {
                    cell1.setFill(Color.LIGHTGRAY);
                    cell1.setAvaliable(false);
                }
            }
        }
    }

    public static void startGame(Board enemyBoard, Board playerBoard) {


        if (playerBoard.getShipsPlaced() == 5) {
            Text start = new Text("Game started!");
            start.setFont(new Font (20));

            gameWindow.setComunicates(start);
            gameWindow.setStoredShips();

            for (Cell cell : enemyBoard.getCellList()) {
                cell.setOnMouseClicked(event -> {
                    enemyMissed = false;
                    if (enemyBoard.isEnemy() && cell.containsShip() && cell.neverShot()) {
                        cell.setFill(Color.RED);
                        cell.setHasShip(false);
                        cell.setWasShot(true);
                        Text hitOnTarget = new Text("Nice shot, enemy ship on fire");
                        hitOnTarget.setFont(new Font (20));
                        gameWindow.setComunicates(hitOnTarget);
                    } else if (cell.neverShot()) {
                        cell.setFill(Color.BLACK);
                        cell.setWasShot(true);
                        Text missed = new Text("This time, you missed");
                        missed.setFont(new Font (20));
                        gameWindow.setComunicates(missed);

                        while (!enemyMissed) {

                            while (!notTheSameTarget) {
                                Random generateX = new Random();
                                Random generateY = new Random();
                                enemyShootX = generateX.nextInt(10);
                                enemyShootY = generateY.nextInt(10);
                                if (previousX != enemyShootX || previousY != enemyShootY) {
                                    notTheSameTarget = true;
                                }
                            }
                            notTheSameTarget = false;
                            previousX = enemyShootX;
                            previousY = enemyShootY;
                            for (Cell cell1 : playerBoard.getCellList()) {
                                if (cell1.getCellX() == enemyShootX && cell1.getCellY() == enemyShootY) {
                                    if (cell1.containsShip() && cell1.neverShot()) {
                                        cell1.setWasShot(true);
                                        cell1.setHasShip(false);
                                        cell1.setFill(Color.RED);
                                        Text enemyHit = new Text("Bad news, enemy just set your ship on fire");
                                        enemyHit.setFont(new Font (20));
                                        gameWindow.setComunicates(enemyHit);
                                    } else if (!cell1.containsShip() && cell1.neverShot()) {
                                        cell1.setWasShot(true);
                                        cell1.setFill(Color.BLACK);
                                        enemyMissed = true;
                                    }
                                }
                            }
                        }
                    }
                    if (enemyBoard.hasNoShip()){
                        for (Cell cell1 : enemyBoard.getCellList()) {
                            cell1.setOnMouseClicked(null);
                        }
                        PopUpWindow.display("You WON !");
                    }else if (playerBoard.hasNoShip()) {
                        for (Cell cell1 : enemyBoard.getCellList()) {
                            cell1.setOnMouseClicked(null);
                        }
                        PopUpWindow.display("You LOST !");
                    }

                });
            }
        } else {
            Text text = new Text("Please place your all ships");
            text.setFont(new Font(20));
            gameWindow.setComunicates(text);
        }
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
}


