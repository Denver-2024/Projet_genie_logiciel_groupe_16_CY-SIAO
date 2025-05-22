package com.cy_siao.view;

import java.io.IOException;

import com.cy_siao.controller.gui.DashboardController;
import com.cy_siao.controller.gui.RoomControllerFx;

import com.cy_siao.view.ViewManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardView{

    private Parent view;
    private DashboardController controller;

    public DashboardView(ViewManager viewManager) {
        // Charge le FXML avec contrôleur injecté
        this.controller = new DashboardController();
        try {
            // Chemin relatif depuis resources
            URL fxmlUrl = getClass().getClassLoader().getResource("dashboard_view.fxml");

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

    public DashboardController getController() {
        return controller;
    }
}