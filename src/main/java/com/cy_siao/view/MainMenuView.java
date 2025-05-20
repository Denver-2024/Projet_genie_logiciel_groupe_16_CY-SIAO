package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.MainMenuController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuView {
    
    private Parent view;
    private MainMenuController controller;

    public MainMenuView() {
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
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML view", e);
        }
    }

    public Parent getView() {
        return view;
    }

    public MainMenuController getController() {
        return controller;
    }

    // Méthode utilitaire pour montrer la vue dans une nouvelle fenêtre
    public void showInStage(Stage stage) {
        stage.setScene(new Scene(view));
        stage.setTitle("CY SIAO - Main Menu");
        stage.show();
    }
}