package com.kodilla.battleship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class GameWindow {
    private BorderPane mainPane = new BorderPane();
    private Main mainWindow;

    private Board playerBoard = new Board(false);
    private GridPane playerGrid = playerBoard.getGrid();
    private Board enemyBoard = new Board(true);
    private GridPane enemyGrid = enemyBoard.getGrid();

    private Label labelPatrolBoat = new Label("Patrol Boat");
    private Label labelCruiser = new Label("Cruiser");
    private Label labelCruiser2 = new Label("Cruiser");
    private Label labelBattleship = new Label("Battleship");
    private Label labelCarrier = new Label("Carrier");
    private TextFlow comunicates = new TextFlow();
    private Messages messages = new Messages();
    private Button start = new Button("Start");
    private double x;
    private double y;




    public GameWindow(){
//        Image imageback = new Image("file:C:\\Development\\Projects\\BattleshipSimple\\src\\main\\resources\\battleship.gif");
//        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
//        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//        Background background = new Background(backgroundImage);

        //Placing board with labels
        VBox gamesBoards = new VBox();
        Label enemyLabel = new Label("Enemy Board");
        Label playerLabel = new Label("Player Board");
        gamesBoards.getChildren().addAll(enemyLabel, enemyGrid, playerLabel, playerGrid);
        gamesBoards.setSpacing(10);
        gamesBoards.setAlignment(Pos.CENTER);
        gamesBoards.toBack();

        //Creating Ships
        Ship patrolBoat = new Ship(20,20, Color.DARKBLUE, 2, playerBoard);
        Ship cruiser = new Ship(20,20, Color.DARKBLUE, 3, playerBoard);
        Ship crusier2 = new Ship(20,20, Color.DARKBLUE, 3, playerBoard);
        Ship battleship = new Ship(20,20, Color.DARKBLUE, 4, playerBoard);
        Ship carrier = new Ship(20,20, Color.DARKBLUE, 5, playerBoard);

        Move moveShip1 = new Move(patrolBoat,playerBoard);
        moveShip1.setShipMoves();
        moveShip1.setBoardReaction();
        moveShip1.setGameWindow(this);
        Move moveShip2 = new Move(cruiser,playerBoard);
        moveShip2.setShipMoves();
        moveShip2.setBoardReaction();
        moveShip2.setGameWindow(this);
        Move moveShip3 = new Move(crusier2,playerBoard);
        moveShip3.setShipMoves();
        moveShip3.setBoardReaction();
        moveShip3.setGameWindow(this);
        Move moveShip4 = new Move(battleship,playerBoard);
        moveShip4.setShipMoves();
        moveShip4.setBoardReaction();
        moveShip4.setGameWindow(this);
        Move moveShip5 = new Move(carrier,playerBoard);
        moveShip5.setShipMoves();
        moveShip5.setBoardReaction();
        moveShip5.setGameWindow(this);

        //Placing ships into a VBox
        VBox storedShips = new VBox();
        storedShips.getChildren().addAll(patrolBoat.getShip(), labelPatrolBoat, cruiser.getShip(), labelCruiser, crusier2.getShip(), labelCruiser2, battleship.getShip(), labelBattleship, carrier.getShip(), labelCarrier);
        storedShips.setPadding(new Insets(10,10,10,50));
        storedShips.setSpacing(10);
        storedShips.setAlignment(Pos.CENTER_LEFT);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        storedShips.setOnMousePressed(event -> {
                storedShips.setMouseTransparent(true);
                event.setDragDetect(true);
        });

        storedShips.setOnDragDetected(event -> {
            storedShips.startFullDrag();
        });


        storedShips.setOnMouseReleased(event -> {
            storedShips.setMouseTransparent(false);
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        start.setOnAction(event -> Move.startGame(enemyBoard,playerBoard));
        start.setPrefSize(100,20);

        //Setting a place for messages
        comunicates.getChildren().add(new Text("Welcom to battleships board game \nPlease place your ships"));
        comunicates.setPadding(new Insets(450,100,10,10));
        comunicates.toBack();

        //Placing everything into a BorderPane

        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setRight(comunicates);
        mainPane.setCenter(storedShips);
        mainPane.setLeft(gamesBoards);
        //mainPane.setBackground(background);


    }

    public TextFlow getComunicates() {
        return comunicates;
    }

    public void setComunicates(Text comunicates) {
        this.comunicates.getChildren().clear();
        this.comunicates.getChildren().add(comunicates);
    }

    public Button getStart() {
        return start;
    }

    public void setMainWindow(Main mainWindow) {
        this.mainWindow = mainWindow;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}