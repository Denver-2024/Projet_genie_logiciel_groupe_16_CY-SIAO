package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.MainMenuController;

import com.cy_siao.view.ViewManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View class for displaying the main menu UI components.
 * Loads the FXML layout and manages the associated controller.
 */
public class MainMenuView {
    
    private final Parent view; // Root node of the view
    private MainMenuController controller; // Controller for the main menu view

    /**
     * Constructs the MainMenuView, loading the FXML and setting up the controller.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public MainMenuView(ViewManager viewManager) {
        // Charge le FXML avec contrôleur injecté
        this.controller = new MainMenuController();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("mainMenu.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found in resources");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            this.view = loader.load();
            controller = loader.getController();
            controller.setViewManager(viewManager);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view", e);
        }
    }

    /**
     * Gets the root node of the main menu view.
     *
     * @return The root Parent node
     */
    public Parent getView() {
        return view;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The MainMenuController instance
     */
    public MainMenuController getController() {
        return controller;
    }
}
