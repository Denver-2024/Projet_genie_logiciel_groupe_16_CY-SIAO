package com.cy_siao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import com.cy_siao.view.ViewManager;

import java.io.InputStream;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CY SIAO Manager");

        //Relative path from resources for  adding the icon to our software
        InputStream stream = getClass().getResourceAsStream("/Images/Projet_SIAO.png");
        try {
            if (stream != null) {
                primaryStage.getIcons().add(new Image(stream));
            }
        } catch (Exception e) {
            System.err.println("Error in loading image: " + e.getMessage());
        }

        new ViewManager(primaryStage); // Initialisation principale
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}