package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class GameWindow {
    private final BorderPane mainPane = new BorderPane();
    private final Board playerBoard = new Board(false);
    private final Board enemyBoard = new Board(true);
    private final TextFlow comunicates = new TextFlow();
    private final Button start = new Button("Start");
    private final Button random = new Button("Randomize Ships");
    private final VBox storedShips = new VBox();
    private final Logic logic = new Logic();



    public GameWindow(){
        Image imageback = new Image("file:src/main/resources/GameWindowBackground.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        //Placing board with labels
        GridPane enemyGrid = enemyBoard.getGrid();
        GridPane playerGrid = playerBoard.getGrid();
        Label labelPatrolBoat = new Label("Patrol Boat");
        Label labelCruiser = new Label("Cruiser");
        Label labelCruiser2 = new Label("Cruiser");
        Label labelBattleship = new Label("Battleship");
        Label labelCarrier = new Label("Carrier");
        VBox gamesBoards = new VBox();
        Label enemyLabel = new Label("Enemy Board");
        Label playerLabel = new Label("Player Board");
        playerLabel.setFont(new Font(20));
        enemyLabel.setFont(new Font(20));
        gamesBoards.getChildren().addAll(enemyLabel, enemyGrid, playerLabel, playerGrid);
        gamesBoards.setSpacing(10);
        gamesBoards.setAlignment(Pos.CENTER);
        gamesBoards.toBack();

        //Creating Ships
        Ship patrolBoat = new Ship(20,20, Color.DARKBLUE, 2);
        Ship cruiser = new Ship(20,20, Color.DARKBLUE, 3);
        Ship crusier2 = new Ship(20,20, Color.DARKBLUE, 3);
        Ship battleship = new Ship(20,20, Color.DARKBLUE, 4);
        Ship carrier = new Ship(20,20, Color.DARKBLUE, 5);

        Action actionShip1 = new Action(patrolBoat,playerBoard);
        actionShip1.setShipMoves();
        actionShip1.setBoardReaction();
        actionShip1.setGameWindow(this);
        Action actionShip2 = new Action(cruiser,playerBoard);
        actionShip2.setShipMoves();
        actionShip2.setBoardReaction();
        actionShip2.setGameWindow(this);
        Action actionShip3 = new Action(crusier2,playerBoard);
        actionShip3.setShipMoves();
        actionShip3.setBoardReaction();
        actionShip3.setGameWindow(this);
        Action actionShip4 = new Action(battleship,playerBoard);
        actionShip4.setShipMoves();
        actionShip4.setBoardReaction();
        actionShip4.setGameWindow(this);
        Action actionShip5 = new Action(carrier,playerBoard);
        actionShip5.setShipMoves();
        actionShip5.setBoardReaction();
        actionShip5.setGameWindow(this);

        //Placing ships into a VBox
        labelPatrolBoat.setFont(new Font(15));
        labelCruiser.setFont(new Font(15));
        labelCruiser2.setFont(new Font(15));
        labelBattleship.setFont(new Font(15));
        labelCarrier.setFont(new Font(15));

        storedShips.getChildren().addAll(patrolBoat.getShip(), labelPatrolBoat, cruiser.getShip(), labelCruiser, crusier2.getShip(), labelCruiser2, battleship.getShip(), labelBattleship, carrier.getShip(), labelCarrier);
        storedShips.setPadding(new Insets(10,10,10,50));
        storedShips.setSpacing(10);
        storedShips.setAlignment(Pos.CENTER_LEFT);
        storedShips.setOnMousePressed(event -> storedShips.setMouseTransparent(true));
        storedShips.setOnMouseReleased(event -> storedShips.setMouseTransparent(false));

        logic.setGameWindow(this);
        start.setOnAction(event -> Logic.startGame(enemyBoard,playerBoard));
        start.setPrefSize(100,20);
        random.setOnAction(event -> {
                playerBoard.randomizeShips();
                storedShips.getChildren().clear();
                random.setDisable(true);
        });

        //Setting a place for messages
        Text welcome = new Text("Welcome to battleships\nPlease place your ships");
        welcome.setFont(new Font(20));
        comunicates.getChildren().add(welcome);
        comunicates.setPadding(new Insets(450,100,10,10));
        comunicates.toBack();

        //Placing everything into a BorderPane
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setRight(comunicates);
        mainPane.setCenter(storedShips);
        mainPane.setLeft(gamesBoards);
        mainPane.setBackground(background);
    }

    public void setComunicates(Text comunicates) {
        this.comunicates.getChildren().clear();
        this.comunicates.getChildren().add(comunicates);
    }

    public Button getStart() {
        return start;
    }

    public Button getRandom() {
        return random;
    }

    public void setMainWindow(Main mainWindow) {
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public void setStoredShips() {
        this.storedShips.getChildren().clear();
    }
}