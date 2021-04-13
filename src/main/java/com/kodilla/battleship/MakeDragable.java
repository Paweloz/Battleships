package com.kodilla.battleship;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
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

    //            //  Próba pobrania komórki na którą najechał statek
//            Bounds boundsInScene = board.localToScene(board.getBoundsInLocal());
//
//            for (int i=0;i<board.getChildren().size();i++) {
//                   Xb = (int) (board.getChildren().get(i)).localToParent(board.getBoundsInParent()).getCenterX();
//                   Yb = (int) board.getChildren().get(i).localToParent(board.getBoundsInParent()).getCenterY();
//                System.out.println(Xb + " "+Yb+" event myszki : "+ (int)event.getSceneX()+" "+(int)event.getSceneY());
//                   }


    public Pane initialiaze(Pane pane, GridPane board){

        pane.setOnMousePressed(event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                pane.setMouseTransparent(true);
                event.setDragDetect(true);
                //pane.getParent().toFront();
                x = pane.getTranslateX() - event.getSceneX();
                y = pane.getTranslateY() - event.getSceneY();
            } else {
                pane.getChildren().get(0).setRotate(90);
            }
        });

        pane.setOnDragDetected(event -> {
            pane.startFullDrag();
        });

        pane.setOnMouseDragged(event -> {

            pane.setTranslateX(event.getSceneX() + x);
            pane.setTranslateY(event.getSceneY() + y);
        });

        pane.setOnMouseReleased(event -> {
            pane.setMouseTransparent(false);
        });

        return pane;
    }

}
