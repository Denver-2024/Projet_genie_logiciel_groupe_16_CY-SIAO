package com.cy_siao.controller.gui;

import com.cy_siao.controller.gui.GUIPersonController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;


public class GUIController {

    private final GUIPersonController personController = new GUIPersonController();


    @FXML
    private MenuButton MainMenu;
    @FXML
    private MenuItem managePersonsMenuItem;

    @FXML
    private MenuItem manageStaysMenuItem;

    @FXML
    private MenuItem manageBedsMenuItem;

    @FXML
    private MenuItem manageRoomsMenuItem;

    @FXML
    private Button helpButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button aboutUsButton;

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }


    @FXML
    private void handleManagePersons(ActionEvent event) {
        System.out.println("Manage Persons clicked");

        GUIPersonController guiPersonController = new GUIPersonController();
        try {
            guiPersonController.goToMainMenu(event); // Affiche PersonManagement.fxml
        } catch (IOException e) {
            e.printStackTrace();
            showError("Unable to open Person Management view.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleManageStays(ActionEvent event) {
        System.out.println("Manage Stays clicked");
    }

    @FXML
    private void handleManageBeds(ActionEvent event) {
        System.out.println("Manage Beds clicked");
    }

    @FXML
    private void handleManageRooms(ActionEvent event) {
        System.out.println("Manage Rooms clicked");
    }

    @FXML
    private void handleHelp(ActionEvent event) {
        System.out.println("Help clicked");
    }

    @FXML
    private void handleAboutUs(ActionEvent event) {
        System.out.println("About Us clicked");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exit clicked");
        System.exit(0);
    }
}

