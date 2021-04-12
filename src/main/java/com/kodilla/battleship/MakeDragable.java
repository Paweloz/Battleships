package com.kodilla.battleship;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/* Klasa udostępniająca funkcjonalność typu press drag release dla obiektów typu Pane oraz Ship */

public class MakeDragable {
    private double x;
    private double y;
    private double Xb;
    private double Yb;
    private Cell cell;


    public Pane initialiaze(Pane pane, GridPane board){

        pane.setOnMousePressed(event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                pane.getParent().toFront();
                x = pane.getTranslateX() - event.getSceneX();
                y = pane.getTranslateY() - event.getSceneY();
                double Y= pane.getChildren().get(0).getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
                double X= pane.getChildren().get(0).getBoundsInLocal().getWidth();
            } else {
                pane.getChildren().get(0).setRotate(90);
                double X= pane.getChildren().get(0).getBoundsInLocal().getHeight(); // Metoda, która wypluwa mi rozmiar grupy obiektów
                double Y= pane.getChildren().get(0).getBoundsInLocal().getWidth();
            }});

        pane.setOnDragOver(event -> {

        });
        pane.setOnMouseReleased(event -> {
            //  Próba pobrania komórki na którą najechał statek
            for (int i=0;i<board.getChildren().size();i++) {
                   Xb =  board.getChildren().get(i).getScene().getWidth();
                   Yb = board.getChildren().get(i).getScene().getHeight();
                   if(board.contains(event.getX(),event.getY())){
                       System.out.println("jest taki");
                   }

                }
            System.out.println(board.getChildren().size());
            //System.out.println("Xb + Yb" +Xb +" "+Yb);
        });
        return pane;
    }

}
