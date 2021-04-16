package com.kodilla.battleship;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/* Główna klasa aplikacji, odpowiadająca za wyświetlanie głownego okna aplikacji.
   Udostępnia widok menu */

public class Main extends Application {
    private Scene scene,gameScene, instructionScene ;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Image imageback = new Image("file:C:\\Development\\Projects\\BattleshipSimple\\src\\main\\resources\\Battleships.png");
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);

            // Menu Layout
            Button instruction = new Button("Instruction");
            instruction.setOnAction(event ->primaryStage.setScene(instructionScene));

            Button startGame = new Button("Start Game");
            startGame.setOnAction(event -> primaryStage.setScene(gameScene));

            Button quitGame = new Button("Quit Game");
            quitGame.setOnAction(e -> primaryStage.close());

            //Umieszczenie przycisków na dole ekranu w HBoxie
            HBox bottom = new HBox();
            bottom.setPadding(new Insets(10,10,30,10));
            bottom.setSpacing(100);
            bottom.setAlignment(Pos.CENTER);
            bottom.getChildren().addAll(startGame,instruction,quitGame);
            BorderPane mainPane = new BorderPane();
            mainPane.setBackground(background);
            mainPane.setBottom(bottom);
            scene = new Scene(mainPane,700,500);


            // Instruction Layout
            // Pobranie widoku okna instrukcji oraz dodanie do niego przycisku powrotu
            Button returnButton = new Button("Back to menu");
            returnButton.setOnAction(event -> primaryStage.setScene(scene));

            InstructionWindow instructionWindow = new InstructionWindow();
            BorderPane instructionLayout = instructionWindow.getLayout();
            HBox bottomButton = new HBox(returnButton);
            bottomButton.setAlignment(Pos.BOTTOM_RIGHT);
            bottomButton.setPadding(new Insets(10,50,10,10));
            instructionLayout.setBottom(bottomButton);
            instructionScene = new Scene(instructionLayout,700,500);

            //Game Layout
            //Pobranie widoku okna gry oraz dodanie do niego przycisku powrotu
            Button returnButton2 = new Button("Back to menu");
            returnButton2.setOnAction(event -> primaryStage.setScene(scene));

            GameWindow gameWindow = new GameWindow();
            BorderPane gameLayout = gameWindow.getMainPane();
            HBox bottomButton2 = new HBox(returnButton2);
            bottomButton2.setAlignment(Pos.BOTTOM_RIGHT);
            bottomButton2.setPadding(new Insets(10,50,10,10));
            gameLayout.setBottom(bottomButton2);
            gameScene = new Scene(gameLayout,700,650);


            //Display
            primaryStage.setScene(scene);
            primaryStage.setTitle("Battleship");
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}