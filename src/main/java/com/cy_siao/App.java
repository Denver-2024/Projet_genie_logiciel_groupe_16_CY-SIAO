package com.cy_siao;

import javafx.application.Application;

import javax.swing.text.View;

import com.cy_siao.view.ViewManager;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CY SIAO Manager");
        new ViewManager(primaryStage); // Initialisation principale
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}