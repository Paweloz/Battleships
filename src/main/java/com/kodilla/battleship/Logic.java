package com.kodilla.battleship;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    private static GameWindow gameWindow;
    private final List<Cell> cellsWithShip = new ArrayList<>();
    private boolean enemyMissed = false;
    private boolean notTheSameTarget = false;
    private double enemyShootY;
    private double enemyShootX;
    private double previousX;
    private double previousY;
    private Cell droppedCell;
    private boolean canPlaceShip;

    // Metoda sprawdzajaca możliwość upuszczenia statku na planszy
    public void findSpaceForShip(int pickedRectangle, double shipTotalX, double shipTotalY,
                                 Board board, boolean vertical, Group shipGroup, Ship ship) {
        List<Cell> cellList = new ArrayList<>();
        droppedCell = getCellWithDroppedShip(board);
        pickedRectangle = calculatePickedRectangle(pickedRectangle, shipGroup, ship);

        if (!vertical) {
            canPlaceShip = canBePlacedHorizontaly(pickedRectangle, shipTotalX,
                    board, droppedCell, cellList);
        } else {
            canPlaceShip = canBePlacedVertical(pickedRectangle, shipTotalY,
                    board, droppedCell, cellList);
        }
        placeShip(board, shipGroup, ship, cellList, canPlaceShip);
    }

    private boolean canBePlacedVertical(int pickedRectangle, double shipTotalY, Board board,
                                        Cell droppedCell, List<Cell> cellList) {
        double requiredOnTheTop = pickedRectangle - 1;
        double requiredOnTheBottom = shipTotalY / 20 - pickedRectangle;
        if (droppedCell != null && droppedCell.isAvaliable()) {
            for (Cell cell : board.getCellList()) {
                if (cell.equals(droppedCell)) {
                    cellList.add(cell);
                    cell.setAvaliable(false);
                }
            }
            findCellsOnTheBottom(board, droppedCell, cellList, requiredOnTheTop);
            findCellsOnTheTop(board, droppedCell, cellList, requiredOnTheBottom);
        }
        return canPlaceShip;
    }

    private void findCellsOnTheBottom(Board board, Cell droppedCell, List<Cell> cellList, double requiredOnTheTop) {
        for (double i = droppedCell.getCellY() - 1; i >= (droppedCell.getCellY() - requiredOnTheTop); i--) {
            for (Cell cell : board.getCellList()) {
                if (cell.getCellY() == i && cell.getCellX() == droppedCell.getCellX()) {
                    if (cell.isAvaliable()) {
                        cellList.add(cell);
                        canPlaceShip = true;
                    } else {
                        canPlaceShip = false;
                    }
                }
            }
        }
    }

    private void findCellsOnTheTop(Board board, Cell droppedCell, List<Cell> cellList, double requiredOnTheBottom) {
        for (double j = droppedCell.getCellY(); j <= (droppedCell.getCellY() + requiredOnTheBottom); j++) {
            for (Cell cell : board.getCellList()) {
                if (cell.getCellY() == j && cell.getCellX() == droppedCell.getCellX()) {
                    if (cell.isAvaliable()) {
                        cellList.add(cell);
                        canPlaceShip = true;
                    }else {
                        canPlaceShip  = false;
                    }
                }
            }
        }
    }

    private boolean canBePlacedHorizontaly(int pickedRectangle, double shipTotalX, Board board, Cell droppedCell,
                                           List<Cell> cellList) {
        double requiredOnTheLeft = pickedRectangle - 1;
        double requiredOnTheRight = shipTotalX / 20 - pickedRectangle;
        if (droppedCell != null && droppedCell.isAvaliable()) {
            findCellsOnTheLeft(board, droppedCell, cellList, requiredOnTheLeft);
            findCellsOnTheRight(board, droppedCell, cellList, requiredOnTheRight);
        }
        return canPlaceShip;
    }

    private void findCellsOnTheLeft(Board board, Cell droppedCell, List<Cell> cellList, double requiredOnTheLeft) {
        for (double i = droppedCell.getCellX(); i >= (droppedCell.getCellX() - requiredOnTheLeft); i--) {
            for (Cell cell : board.getCellList()) {
                if (cell.getCellX() == i && cell.getCellY() == droppedCell.getCellY()) {
                    if (cell.isAvaliable()) {
                        cellList.add(cell);
                        canPlaceShip = true;
                    } else {
                        canPlaceShip = false;
                    }
                }
            }
        }
    }

    private void findCellsOnTheRight(Board board, Cell droppedCell, List<Cell> cellList, double requiredOnTheRight) {
        for (double j = droppedCell.getCellX() + 1; j <= (droppedCell.getCellX() + requiredOnTheRight); j++) {
            for (Cell cell : board.getCellList()) {
                if (cell.getCellX() == j && cell.getCellY() == droppedCell.getCellY()) {
                    if (cell.isAvaliable()) {
                        cellList.add(cell);
                        canPlaceShip = true;
                    }else {
                        canPlaceShip  = false;
                    }
                }
            }
        }
    }

    private int calculatePickedRectangle(int pickedRectangle, Group shipGroup, Ship ship) {
        if(shipGroup.getRotate() == 180 || shipGroup.getRotate()%360 == 180
                || shipGroup.getRotate() == 270 || shipGroup.getRotate()%360==270){
            if(pickedRectangle == ship.getType()){
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
        return pickedRectangle;
    }

    private Cell getCellWithDroppedShip(Board board) {
        for (Cell cell : board.getCellList()) {
            if (cell.isShipDroppedOn()) {
                droppedCell = cell;
                cell.setShipDroppedOn(false);
            }
        }
        return droppedCell;
    }

    private void placeShip(Board board, Group shipGroup, Ship ship, List<Cell> cellList, boolean canPlaceShip) {
        if (canPlaceShip) {
            if (cellList.size() == ship.getType()) {
                putShipOnBoard(board, shipGroup, cellList);
            } else {
                shipGroup.setVisible(true);
                Text wrongPlace = new Text("You cannot place your ship here");
                wrongPlace.setFont(new Font(20));
                gameWindow.setComunicates(wrongPlace);
            }
        }
    }

    private void putShipOnBoard(Board board, Group shipGroup, List<Cell> cellList) {
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
        board.setNeighborhood(cellsWithShip);
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
    }

    public void startGame(Board enemyBoard, Board playerBoard) {
        if (playerBoard.getShipsPlaced() == 5) {
            Text start = new Text("Game started!");
            start.setFont(new Font (20));
            gameWindow.setComunicates(start);
            gameWindow.setStoredShips();
            gameWindow.getStart().setDisable(true);

            startPlaying(enemyBoard, playerBoard);
        } else {
            Text text = new Text("Please place your all ships");
            text.setFont(new Font(20));
            gameWindow.setComunicates(text);
        }
    }

    private void startPlaying(Board enemyBoard, Board playerBoard) {
        for (Cell cell : enemyBoard.getCellList()) {
            cell.setOnMouseClicked(event -> {
                enemyMissed = false;
                shooting(enemyBoard, playerBoard, cell);
                checkGameStatus(enemyBoard, playerBoard);

            });
        }
    }

    private void checkGameStatus(Board enemyBoard, Board playerBoard) {
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
    }

    private void shooting(Board enemyBoard, Board playerBoard, Cell cell) {
        if (enemyBoard.isEnemy() && cell.containsShip() && cell.neverShot()) {
            playerHitShip(cell);
        } else if (cell.neverShot()) {
            playerMissed(playerBoard, cell);
        }
    }

    private void playerMissed(Board playerBoard, Cell cell) {
        cell.setFill(Color.BLACK);
        cell.setWasShot(true);
        Text missed = new Text("This time, you missed");
        missed.setFont(new Font (20));
        gameWindow.setComunicates(missed);
        cpuShooting(playerBoard);
    }

    private void playerHitShip(Cell cell) {
        cell.setFill(Color.RED);
        cell.setHasShip(false);
        cell.setWasShot(true);
        Text hitOnTarget = new Text("Nice shot, enemy ship on fire");
        hitOnTarget.setFont(new Font (20));
        gameWindow.setComunicates(hitOnTarget);
    }

    private void cpuShooting(Board playerBoard) {
        while (!enemyMissed) {
            generateCPUTarget();
            notTheSameTarget = false;
            previousX = enemyShootX;
            previousY = enemyShootY;
            for (Cell cell1 : playerBoard.getCellList()) {
                if (cell1.getCellX() == enemyShootX && cell1.getCellY() == enemyShootY) {
                    if (cell1.containsShip() && cell1.neverShot()) {
                        cpuHitShip(cell1);
                    } else if (!cell1.containsShip() && cell1.neverShot()) {
                        cpuMissed(cell1);
                    }
                }
            }
        }
    }

    private void cpuMissed(Cell cell1) {
        cell1.setWasShot(true);
        cell1.setFill(Color.BLACK);
        enemyMissed = true;
    }

    private void cpuHitShip(Cell cell1) {
        cell1.setWasShot(true);
        cell1.setHasShip(false);
        cell1.setFill(Color.RED);
        Text enemyHit = new Text("Bad news, enemy just set your ship on fire");
        enemyHit.setFont(new Font (20));
        gameWindow.setComunicates(enemyHit);
    }

    private void generateCPUTarget() {
        while (!notTheSameTarget) {
            Random generateX = new Random();
            Random generateY = new Random();
            enemyShootX = generateX.nextInt(10);
            enemyShootY = generateY.nextInt(10);
            if (previousX != enemyShootX || previousY != enemyShootY) {
                notTheSameTarget = true;
            }
        }
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
}