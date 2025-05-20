package com.cy_siao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Relative path from resources for  adding the icon to our software
        InputStream stream = getClass().getResourceAsStream("/Images/Projet_SIAO.png");
        try {
            if (stream != null) {
                primaryStage.getIcons().add(new Image(stream));
            }
        } catch (Exception e) {
            System.err.println("Error in loading image: " + e.getMessage());
        }

        primaryStage.setTitle("CY SIAO Application");
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainMenu.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error in loading the scene: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}