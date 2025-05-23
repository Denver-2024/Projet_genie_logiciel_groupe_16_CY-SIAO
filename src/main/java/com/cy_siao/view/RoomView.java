package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.RoomControllerFx;

import com.cy_siao.view.ViewManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View class for displaying room-related UI components.
 * Loads the FXML layout and manages the associated controller.
 */
public class RoomView {
    
    private Parent view; // Root node of the view
    private RoomControllerFx controller; // Controller for the room view

    /**
     * Constructs the RoomView, loading the FXML and setting up the controller.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public RoomView(ViewManager viewManager) {
        // Charge le FXML avec contrôleur injecté
        this.controller = new RoomControllerFx();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("room_view.fxml");
            
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
     * Gets the root node of the room view.
     *
     * @return The root Parent node
     */
    public Parent getView() {
        return view;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The RoomControllerFx instance
     */
    public RoomControllerFx getController() {
        return controller;
    }
}
