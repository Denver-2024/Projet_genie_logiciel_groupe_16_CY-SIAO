package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.BedControllerFx;

import com.cy_siao.view.ViewManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BedView {
    
    private Parent view;
    private BedControllerFx controller;

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
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view", e);
        }
    }

    public Parent getView() {
        return view;
    }

    public BedControllerFx getController() {
        return controller;
    }
}