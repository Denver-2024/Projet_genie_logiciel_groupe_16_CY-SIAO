package com.cy_siao.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.cy_siao.controller.gui.FindStayControllerFx;
import com.cy_siao.controller.gui.MainMenuController;
import com.cy_siao.controller.gui.PersonControllerFx;
import com.cy_siao.view.ViewManager;

public class FindStayView {
    private Parent view;
    private FindStayControllerFx controller;

    public FindStayView(ViewManager viewManager) {
        this.controller = new FindStayControllerFx();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("findStay_view.fxml");
            
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

    public FindStayControllerFx getController() {
        return controller;
    }

}