package com.cy_siao.controller.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import com.cy_siao.view.ViewManager;

public class MainMenuController implements Initializable {

    private ViewManager viewManager; 

     @FXML
    private VBox mainContainer;

    @FXML
    private Button managePersonButton;

    @FXML
    private Button manageBedButton;

    @FXML
    private Button manageRoomButton;

    @FXML
    private Button manageStayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtonActions();
        System.out.println("Controller initialized successfully!");
    }

    public void setViewManager(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    private void setupButtonActions() {
        managePersonButton.setOnAction(event -> handleManagePersons());
        manageBedButton.setOnAction(event -> handleManageBeds());
        manageRoomButton.setOnAction(event -> handleManageRooms());
        manageStayButton.setOnAction(event -> handleManageStays());
    }

    @FXML
    private void handleManagePersons() {
        viewManager.showPersonView();
    }

    @FXML
    private void handleManageBeds() {
        viewManager.showBedView();
    }

    @FXML
    private void handleManageRooms() {
        viewManager.showRoomView();
    }

    @FXML
    private void handleManageStays() {
        viewManager.showStayView();
    }
}
