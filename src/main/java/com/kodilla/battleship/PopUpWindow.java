package com.kodilla.battleship;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindow {
    private static Stage stage;
    private static boolean result;

    public static boolean display(String title, String message){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        VBox layout = new VBox();
        Label messageLabel = new Label(message);
        messageLabel.setFont(new Font(25));

        Button confirmButton = new Button("Yes");
        confirmButton.setPrefSize(50,20);
        confirmButton.setOnAction(event -> {
            result=true;
            stage.close();
        });
        Button cancelButton = new Button("No");
        cancelButton.setPrefSize(50,20);
        cancelButton.setOnAction(event -> {
            result=false;
            stage.close();
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(confirmButton,cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);

        layout.getChildren().addAll(messageLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        Scene popUpScene = new Scene(layout);
        stage.setScene(popUpScene);
        stage.setMinWidth(600);
        stage.showAndWait();
        return result;
    }
    public static void  display(String message) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Battleship");

        VBox layout = new VBox();
        Label messageLabel = new Label(message);
        messageLabel.setFont(new Font(25));

        layout.getChildren().addAll(messageLabel);
        layout.setAlignment(Pos.CENTER);
        Scene popUpScene = new Scene(layout);
        stage.setScene(popUpScene);
        stage.setMinWidth(400);
        stage.setMinHeight(200);
        stage.showAndWait();
    }

}
