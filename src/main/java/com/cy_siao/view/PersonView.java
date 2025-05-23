package com.cy_siao.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.cy_siao.controller.gui.MainMenuController;
import com.cy_siao.controller.gui.PersonControllerFx;
import com.cy_siao.view.ViewManager;

/**
 * View class for displaying person-related UI components.
 * Loads the FXML layout and manages the associated controller.
 */
public class PersonView {
    private Parent view; // Root node of the view
    private PersonControllerFx controller; // Controller for the person view

    /**
     * Constructs the PersonView, loading the FXML and setting up the controller.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public PersonView(ViewManager viewManager) {
        this.controller = new PersonControllerFx();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("person_view.fxml");
            
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
     * Gets the root node of the person view.
     *
     * @return The root Parent node
     */
    public Parent getView() {
        return view;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The PersonControllerFx instance
     */
    public PersonControllerFx getController() {
        return controller;
    }

}
