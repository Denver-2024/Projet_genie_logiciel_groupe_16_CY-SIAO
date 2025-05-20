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
    private Button managePersonButton;

    @FXML
    private Button manageRoomButton;

    @FXML
    private Button manageStayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtonActions();
        System.out.println("Controller initialized successfully!");
    }

    private void setupButtonActions() {
        managePersonButton.setOnAction(event -> handleManagePersons());
        manageRoomButton.setOnAction(event -> handleManageRooms());
        manageStayButton.setOnAction(event -> handleManageStays());
    }

    @FXML
    private void handleManagePersons() {
        System.out.println("Manage Persons button clicked!");
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
