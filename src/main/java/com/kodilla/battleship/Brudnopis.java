package com.kodilla.battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.util.Random;


public class Brudnopis extends Application {

    private char player = 'X';
    private Cell[][] board = new Cell[3][3];
    private Button button = new Button("Start new game");
    private Label label = new Label("");
    private GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        gridPane.setPadding(new Insets(40));
        gridPane.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(50));

        EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createNewBoard();
            }
        };
        button.setOnAction(buttonEvent);

        BorderPane.setAlignment(button, Pos.BOTTOM_CENTER);
        borderPane.setBottom(button);

        BorderPane.setAlignment(label, Pos.TOP_CENTER);
        label.setFont(new Font(30));
        borderPane.setTop(label);

        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 540, 550);
        primaryStage.setTitle("Tic Tac Toe Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void createNewBoard() {
        gridPane.getChildren().clear();
        label.setText("");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[j][i] = new Cell();
                gridPane.add(board[j][i], j, i);
                player = 'X';
            }
        }
    }

    //determine if there is empty token
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[j][i].getToken() == ' ') {
                    return false; //continue game if there is empty token
                }
            }
        }
        return true; //break game if board if full
    }

    //determine if any player won
    public boolean ifPlayerWon(char token) {

        //horizontal or vertical
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getToken() == token && board[i][1].getToken() == token && board[i][2].getToken() == token ||
                    board[0][i].getToken() == token && board[1][i].getToken() == token && board[2][i].getToken() == token) {
                return true;
            }
        }

        //diagonal
        if (board[0][0].getToken() == token && board[1][1].getToken() == token && board[2][2].getToken() == token ||
                board[0][2].getToken() == token && board[1][1].getToken() == token && board[2][0].getToken() == token) {
            return true;

        }
        return false;
    }

    public class Cell extends Pane {

        private char token = ' ';
        private Random random = new Random();

        public Cell() {
            setStyle("-fx-border-color: black");
            setPrefSize(100, 100);
            setOnMouseClicked(event -> mouseEvent());
        }

        //move of computer player
        private void computerMove() {
            int temp = 0;
            while (temp == 0) {
                int randomRow = random.nextInt(3);
                int randomColumn = random.nextInt(3);
                if (board[randomRow][randomColumn].getToken() == ' ') {
                    board[randomRow][randomColumn].setToken(player);
                    temp = 1;
                }
            }
        }

        public char getToken() {
            return token;
        }

        //setting tokens
        public void setToken(char sign) {
            token = sign;

            if (token == 'X') {
                Text text = new Text("X");
                setShape(text);
                setStyle("-fx-border-color: blue");


            } else if (token == 'O') {
                Text text = new Text("O");
                setShape(text);
                setStyle("-fx-border-color: red");
            }
        }

        //what happens when player clicks mouse
        private void mouseEvent() {
            if (token == ' ' && player != 'E') { //if cell is empty and game continues
                setToken(player);
                checkState();
            }
        }

        private void checkState() {
            while (player != 'E') { //if game continues
                if (player == 'X') { //if now is player's turn
                    setOnMouseClicked(event -> mouseEvent());

                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game if player won
                        label.setText("YOU WON!");
                    } else if (isBoardFull()) {
                        player = 'E'; //end game if there is no empty token
                    } else {
                        player = 'O'; //change player
                    }

                } else if (player == 'O') { //if now is computer's turn
                    computerMove();

                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game if computer won
                        label.setText("YOU LOST!");
                    } else if (isBoardFull()) {
                        player = 'E'; //end game if there is no empty token
                    } else {
                        player = 'X'; //change player
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}