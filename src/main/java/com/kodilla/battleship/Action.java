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
public class Action {
    private static GameWindow gameWindow;
    private double draggingX;
    private double draggingY;
    private final Ship ship;
    private final Group shipGroup;
    private final Board board;

    private final Logic logic = new Logic();


    public Action(Ship ship, Board board) {
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
                if (shipGroup.getRotate() == 90 || shipGroup.getRotate() == 270 || shipGroup.getRotate() % 360 == 90 ||
                        shipGroup.getRotate() % 360 == 270) {
                    ship.setVertical(true);
                    ship.setShipTotalX(ship.getShipHeight());
                    ship.setShipTotalY(ship.getShipWidth() * ship.getType());

                } else {
                    ship.setVertical(false);
                    ship.setShipTotalX(ship.getShipWidth() * ship.getType());
                    ship.setShipTotalY(ship.getShipHeight());
                }
            }
        });


        shipGroup.setOnDragDetected(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shipGroup.startFullDrag();
            }
        });


        shipGroup.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shipGroup.getParent().toFront();
                shipGroup.setTranslateX(event.getSceneX() + draggingX);
                shipGroup.setTranslateY(event.getSceneY() + draggingY);
                event.setDragDetect(false);
            }
        });

        shipGroup.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                shipGroup.setMouseTransparent(false);
                try {
                    logic.placeShip(ship.getPickedRectangle(), ship.getShipTotalX(), ship.getShipTotalY(), board, ship.isVertical(), shipGroup, ship);
                } catch (NullPointerException e) {
                    System.out.println();
                }

            }
        });
    }

    public void setBoardReaction() {
        for (Cell cell : board.getCellList()) {
            cell.setOnMouseDragReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    cell.setShipDroppedOn(true);
                }
            });
        }
    }
    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
}





