package com.cy_siao.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showView(Parent view) {
        primaryStage.setScene(new Scene(view));
    }

    public static void showView(Parent view, int width, int height) {
        primaryStage.setScene(new Scene(view, width, height));
    }
}