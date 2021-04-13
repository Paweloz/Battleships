package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;


public class GameWindow {
    private BorderPane mainPane = new BorderPane();
    private GridPane playerBoard = new Board(false).getGrid();
    private GridPane enemyBoard = new Board(true).getGrid();
    private Pane carrierPane;
    private Pane battleshipPane;
    private Pane cruiserPane;
    private Pane cruiserPane2;
    private Pane patrolBoatPane;
    private Label labelPatrolBoat = new Label("Patrol Boat");
    private Label labelCruiser = new Label("Cruiser");
    private Label labelCruiser2 = new Label("Cruiser");
    private Label labelBattleship = new Label("Battleship");
    private Label labelCarrier = new Label("Carrier");
    private TextFlow comunicates = new TextFlow();
    private Messages messages = new Messages();
    private MakeDragable makeDragable = new MakeDragable();
    private double x;
    private double y;




    public GameWindow(){
        //Placing board with labels
        VBox gamesBoards = new VBox();
        Label enemyLabel = new Label("Enemy Board");
        Label playerLabel = new Label("Player Board");
        gamesBoards.getChildren().addAll(enemyLabel, enemyBoard, playerLabel, playerBoard);
        gamesBoards.setSpacing(10);
        gamesBoards.setAlignment(Pos.CENTER);

        //Creating Ships
        Ship patrolBoat = new Ship(20,20, Color.DARKBLUE, 2);
        Ship cruiser = new Ship(20,20, Color.DARKBLUE, 3);
        Ship crusier2 = new Ship(20,20, Color.DARKBLUE, 3);
        Ship battleship = new Ship(20,20, Color.DARKBLUE, 4);
        Ship carrier = new Ship(20,20, Color.DARKBLUE, 5);

        //Placing ships into a VBox
        VBox storedShips = new VBox();
        storedShips.getChildren().addAll(patrolBoat.getShip(), labelPatrolBoat, cruiser.getShip(), labelCruiser, crusier2.getShip(), labelCruiser2, battleship.getShip(), labelBattleship, carrier.getShip(), labelCarrier);
        storedShips.setPadding(new Insets(10,10,10,50));
        storedShips.setSpacing(10);
        storedShips.setAlignment(Pos.CENTER_LEFT);
        storedShips.toFront();

        //Setting a place for messages
        comunicates.getChildren().add(messages.getPlaceYourShip());
        comunicates.setPadding(new Insets(450,10,10,10));

        //Placing everything into a BorderPane
        mainPane.setRight(comunicates);
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setCenter(storedShips);
        mainPane.setLeft(gamesBoards);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}