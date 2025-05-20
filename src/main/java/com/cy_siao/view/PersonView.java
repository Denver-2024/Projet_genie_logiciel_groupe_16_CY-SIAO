package com.cy_siao.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.cy_siao.controller.gui.MainMenuController;
import com.cy_siao.controller.gui.PersonControllerFx;

public class PersonView {
    private Parent view;
    private PersonControllerFx controller;

    public PersonView() {
        this.controller = new PersonControllerFx();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("person_view.fxml");
            
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

    public PersonControllerFx getController() {
        return controller;
    }

    public void showInStage(Stage stage) {
        stage.setScene(new Scene(view));
        stage.setTitle("CY SIAO - Main Menu");
        stage.show();
    }
}