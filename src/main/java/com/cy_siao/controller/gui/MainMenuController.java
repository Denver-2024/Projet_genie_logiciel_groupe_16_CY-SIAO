package com.cy_siao.controller.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private VBox mainContainer;

    @FXML
    private Button btnManagePersons;

    @FXML
    private Button btnManageRooms;

    @FXML
    private Button btnManageStays;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize controller
    }

    @FXML
    private void handleManagePersons() {

    }

    @FXML
    private void handleManageRooms() {
        // Handle rooms management
    }

    @FXML
    private void handleManageStays() {
        // Handle stays management
    }
}
