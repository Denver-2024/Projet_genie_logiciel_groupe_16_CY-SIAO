package com.cy_siao;

import com.cy_siao.controller.GUIController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for Graphical user interface (GUI)
 */
public class GuiApp extends Application {
    
    /**
     * start GUI
     */
    @Override
    public void start(Stage primaryStage) {
        GUIController controller = new GUIController(primaryStage);
        controller.startApplication();
    }

    /**
     * start Gui
     * @param args start args when we launch program
     */
    public void start(String[] args) {
        launch(args);
    }
}