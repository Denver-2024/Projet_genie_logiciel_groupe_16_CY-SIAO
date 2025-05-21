package com.cy_siao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import com.cy_siao.view.ViewManager;

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