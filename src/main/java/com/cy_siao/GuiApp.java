package com.cy_siao;

import com.cy_siao.controller.GUIController;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GUIController controller = new GUIController(primaryStage);
        controller.startApplication();
    }

    public static void main(String[] args) {
        launch(args);
    }
}