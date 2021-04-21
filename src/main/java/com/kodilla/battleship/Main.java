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
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            Image imageback = new Image("file:src/main/resources/Battleships.png");
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);


            // Menu Layout
            Button instruction = new Button("Instruction");
            instruction.setPrefSize(80,20);
            instruction.setOnAction(event ->primaryStage.setScene(instructionScene));

            Button startGame = new Button("Game");
            startGame.setPrefSize(80,20);
            startGame.setOnAction(event -> primaryStage.setScene(gameScene));

            Button quitGame = new Button("Quit Game");
            quitGame.setPrefSize(80,20);
            quitGame.setOnAction(e -> {
                boolean result = PopUpWindow.display("Battleships", "Are you sure, you want to quit ?");
                if (result){
                    primaryStage.close();
                }
            });

            Button reset = new Button("Restart");
            reset.setPrefSize(80,20);
            reset.setOnAction(event -> initGame());

            //Umieszczenie przycisków na dole ekranu w HBoxie
            HBox bottom = new HBox();
            bottom.setPadding(new Insets(10,10,30,10));
            bottom.setSpacing(50);
            bottom.setAlignment(Pos.CENTER);
            bottom.getChildren().addAll(startGame,reset, instruction,quitGame);
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
            initGame();

            primaryStage.setOnCloseRequest(e -> {
                e.consume();
                boolean result = PopUpWindow.display("Battleships", "Are you sure, you want to quit ?");
                if (result){
                    primaryStage.close();
                }
            });


            //Display
            primaryStage.setScene(scene);
            primaryStage.setTitle("Battleship");
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initGame(){
        Button returnButton2 = new Button("Back to menu");
        returnButton2.setOnAction(event -> stage.setScene(scene));

        GameWindow gameWindow = new GameWindow();
        gameWindow.setMainWindow(this);
        BorderPane gameLayout = gameWindow.getMainPane();
        HBox bottomButtons = new HBox(gameWindow.getStart(), gameWindow.getRandom() ,returnButton2);
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);
        bottomButtons.setPadding(new Insets(10,200,10,0));
        bottomButtons.setSpacing(20);
        gameLayout.setBottom(bottomButtons);
        gameScene = new Scene(gameLayout,750,650);
    }
}