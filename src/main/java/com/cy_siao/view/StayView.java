package com.cy_siao.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import com.cy_siao.controller.gui.MainMenuController;
import com.cy_siao.controller.gui.StayControllerFx;
import com.cy_siao.view.ViewManager;
import com.cy_siao.view.PlanningView;

/**
 * View class for displaying stay-related UI components.
 * Loads the FXML layout and manages the associated controller.
 */
public class StayView {
    private Parent view; // Root node of the view
    private StayControllerFx controller; // Controller for the stay view

    /**
     * Constructs the StayView, loading the FXML and setting up the controller.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public StayView(ViewManager viewManager) {
        this.controller = new StayControllerFx();
        controller.setViewManager(viewManager);
        VBox layout=controller.getView();

        Button planningButton =new Button("See the Beds Schedule");
        planningButton.setOnAction(e ->{
            if (viewManager!=null){
                viewManager.showPlanningView();
            }
        });
        layout.getChildren().add(planningButton);
        this.view=layout;

        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("stay_view.fxml");
            
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
     * Gets the root node of the stay view.
     *
     * @return The root Parent node
     */
    public Parent getView() {
        return view;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The StayControllerFx instance
     */
    public StayControllerFx getController() {
        return controller;
    }

}
