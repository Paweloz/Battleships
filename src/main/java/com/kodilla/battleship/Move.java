package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* Klasa udostępniajaca funkcjonalność ruchu dla statków oraz reakcji dla planszy
*  Zawiera metode sprawdzającą możliwość położenia statku przez gracza,
*  oraz metodę uruchamiającą rozgrywkę */
public class Move {
    private static GameWindow gameWindow;
    private double draggingX;
    private double draggingY;
    private final Ship ship;
    private final Group shipGroup;
    private final Board board;
    private final  List<Cell> cellsWithShip = new ArrayList<>();
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
                Text rotationInfo = new Text("You can rotate your ship with right mouse button");
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
                board.setNeighborhood(cellsWithShip);

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

        // Obrót statku o 180' powoduje zmiane kolejności kwadratów twrzących statek,
        // i wymaga to wzięcia pod uwagę przy liczeniu 'pickedRectangle'
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

        //Sprawdza, czy żadne z potencajlnych pól statku nie zwróciło false
        //i jeśli ilość pól dla statku jest równa wielkości statku to umieszcza statek na planszy
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
                gameWindow.getRandom().setDisable(true);
                if(board.getShipsPlaced()==5){
                    Text shipsDone = new Text("You can start your game now");
                    shipsDone.setFont(new Font(20));
                    gameWindow.setComunicates(shipsDone);
                }
            } else {
                shipGroup.setVisible(true);
                Text wrongPlace = new Text("You cannot place your ship here");
                wrongPlace.setFont(new Font(20));
                gameWindow.setComunicates(wrongPlace);
            }
        }
    }

    /* Jeśli statki są rozmieszczone poprawnie to uruchamia możliwość strzelania.
     Strzelanie jest możliwe dopóki na jednej z planszy nie zostaną zniszczone wszystkie statki
     Komputer strzela losowo, ale nie może strzelić 2 razy w to samo miejsce */
    public static void startGame(Board enemyBoard, Board playerBoard) {
        if (playerBoard.getShipsPlaced() == 5) {
            Text start = new Text("Game started!");
            start.setFont(new Font (20));
            gameWindow.setComunicates(start);
            gameWindow.setStoredShips();
            gameWindow.getStart().setDisable(true);

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

                        //Strzelanie komputera
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


