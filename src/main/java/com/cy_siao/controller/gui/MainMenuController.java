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

/**
 * Controller class for the main menu interface.
 * Handles user interactions and navigation between different views.
 */
public class MainMenuController implements Initializable {

    //Main container for the menu interface
    @FXML
    public VBox mainContainer;

    //Label displaying menu text
    @FXML
    public Label menuText;

    //Text area for long form content
    public TextArea longTextArea;

    //View manager instance for handling view transitions
    @FXML
    private ViewManager viewManager;

    //Button for accessing person management
    @FXML
    private Button managePersonButton;

    //Button for accessing bed management  
    @FXML
    private Button manageBedButton;

    //Button for accessing room management
    @FXML
    private Button manageRoomButton;

    //Button for accessing stay management
    @FXML
    private Button manageStayButton;

    //Button for accessing dashboard
    @FXML
    private Button dashboardButton;

    /**
     * Initializes the controller class.
     * Sets up button actions and performs any necessary initialization.
     *
     * @param location  The location used to resolve relative paths
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtonActions();
        System.out.println("Controller initialized successfully!");
    }

    /**
     * Sets the view manager for this controller.
     *
     * @param viewManager The ViewManager instance to be used
     */
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    //Sets up action handlers for all navigation buttons
    private void setupButtonActions() {
        managePersonButton.setOnAction(event -> handleManagePersons());
        manageBedButton.setOnAction(event -> handleManageBeds());
        manageRoomButton.setOnAction(event -> handleManageRooms());
        manageStayButton.setOnAction(event -> handleManageStays());
        dashboardButton.setOnAction(event -> viewManager.showDashboardView());
    }

    //Handles navigation to person management view
    @FXML
    private void handleManagePersons() {
        viewManager.showPersonView();
    }

    //Handles navigation to bed management view
    @FXML
    private void handleManageBeds() {
        viewManager.showBedView();
    }

    //Handles navigation to room management view
    @FXML
    private void handleManageRooms() {
        viewManager.showRoomView();
    }

    //Handles navigation to stay management view
    @FXML
    private void handleManageStays() {
        viewManager.showStayView();
    }
}