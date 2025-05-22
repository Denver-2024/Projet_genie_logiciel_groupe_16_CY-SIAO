package com.cy_siao.controller.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import com.cy_siao.view.ViewManager;
import javafx.scene.text.TextFlow;

public class MainMenuController implements Initializable {


    @FXML
    public VBox mainContainer;

    @FXML
    public Label menuText;
    public TextArea longTextArea;


    @FXML
    private ViewManager viewManager;



    @FXML
    private Button managePersonButton;

    @FXML
    private Button manageBedButton;

    @FXML
    private Button manageRoomButton;

    @FXML
    private Button manageStayButton;

    @FXML
    private Button dashboardButton;

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
        dashboardButton.setOnAction(event -> viewManager.showDashboardView());
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
