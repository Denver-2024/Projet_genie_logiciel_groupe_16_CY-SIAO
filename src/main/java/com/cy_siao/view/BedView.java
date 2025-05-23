package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.BedControllerFx;

import com.cy_siao.view.ViewManager;
import com.cy_siao.view.PlanningView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * View class for displaying bed-related UI components.
 * Loads the FXML layout and manages the associated controller.
 */
public class BedView {
    
    private Parent view; // Root node of the view
    private BedControllerFx controller; // Controller for the bed view

    /**
     * Constructs the BedView, loading the FXML and setting up the controller.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public BedView(ViewManager viewManager) {
        // Charge le FXML avec contrôleur injecté
        this.controller = new BedControllerFx();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("bed_view.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found in resources");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            this.view = loader.load();
            controller = loader.getController();
            controller.setViewManager(viewManager);

            /*
            VBox layout= controller.getView();

            Button planningButton = new Button("See the Beds Schedule");
            planningButton.setOnAction(e ->{
                if (viewManager != null){
                    viewManager.showPlanningView();
                }
            });
            layout.getChildren().add(planningButton);
            this.view=layout;

             */
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view", e);
        }
    }

    /**
     * Gets the root node of the bed view.
     *
     * @return The root Parent node
     */
    public Parent getView() {
        return view;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The BedControllerFx instance
     */
    public BedControllerFx getController() {
        return controller;
    }
}
