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
    private Pane paneOf5 = new Pane();
    private Pane paneOf4 = new Pane();
    private Pane paneOf3 = new Pane();
    private Pane nextPaneOf3 = new Pane();
    private Pane paneOf2 = new Pane();
    private Label labelShip2 = new Label("Ship Of 2");
    private Label labelShip3 = new Label("Ship Of 3");
    private Label labelShip33 = new Label("2nd Ship Of 3");
    private Label labelShip4 = new Label("Ship Of 4");
    private Label labelShip5 = new Label("Ship Of 5");
    private TextFlow comunicates = new TextFlow();
    private Messages messages = new Messages();




    public GameWindow(){
        //Placing board with labels
        VBox gamesBoards = new VBox();
        Label enemyLabel = new Label("Enemy Board");
        Label playerLabel = new Label("Player Board");
        gamesBoards.getChildren().addAll(enemyLabel, enemyBoard, playerLabel, playerBoard);
        gamesBoards.setSpacing(10);
        gamesBoards.setAlignment(Pos.CENTER);

        //Creating Ships
        Ship shipOf2 = new Ship(20,20, Color.DARKBLUE, 2);
        Ship shipOf3 = new Ship(20,20, Color.DARKBLUE, 3);
        Ship nextShipOf3 = new Ship(20,20, Color.DARKBLUE, 3);
        Ship shipOf4 = new Ship(20,20, Color.DARKBLUE, 4);
        Ship shipOf5 = new Ship(20,20, Color.DARKBLUE, 5);

        //Placing ships into a panes
        paneOf5.getChildren().add(shipOf5.getShip());
        paneOf4.getChildren().add(shipOf4.getShip());
        paneOf3.getChildren().add(shipOf3.getShip());
        nextPaneOf3.getChildren().add(nextShipOf3.getShip());
        paneOf2.getChildren().add(shipOf2.getShip());

        //Placing panes with ships into a VBox
        VBox storedShips = new VBox();
        storedShips.getChildren().addAll(paneOf2,labelShip2,paneOf3,labelShip3,nextPaneOf3,labelShip33,paneOf4,labelShip4,paneOf5,labelShip5);
        storedShips.setPadding(new Insets(10,10,10,50));
        storedShips.setSpacing(10);
        storedShips.setAlignment(Pos.CENTER_LEFT);

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