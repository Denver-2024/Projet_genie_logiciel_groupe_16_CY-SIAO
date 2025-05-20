package com.cy_siao.controller;

import com.cy_siao.view.GuiView;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GUIController {
    private GuiView view;
    private Stage primaryStage;

    public GUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new GuiView(primaryStage, this);
    }

    public void startApplication() {
        view.showMainMenu();
    }

    // Gestionnaires d'événements
    public void handlePersonMenu() {
        view.showPersonMenu();
    }

    public void handleShowAllPersons() {
        // Appel au modèle pour récupérer les personnes
        view.showAlert("Info", "Listing all persons...", Alert.AlertType.INFORMATION);
    }

    public void handleAddPerson() {
        String name = view.showInputDialog("Enter name:");
        String ageInput = view.showInputDialog("Enter age:");
        
        try {
            int age = Integer.parseInt(ageInput);
            // Ici on appellerait le modèle pour ajouter la personne
            view.showAlert("Success", "Person added: " + name + " (" + age + ")", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            view.showAlert("Error", "Please enter a valid number.", Alert.AlertType.ERROR);
            handleAddPerson(); // Retry
        }
    }

    public void handleUpdatePerson() {
        view.showAlert("Info", "Update logic not implemented.", Alert.AlertType.INFORMATION);
    }

    public void handleDeletePerson() {
        view.showAlert("Info", "Delete logic not implemented.", Alert.AlertType.INFORMATION);
    }

    public void handleBackToMain() {
        view.showMainMenu();
    }

    public void showMessage(String message) {
        view.showAlert("Info", message, Alert.AlertType.INFORMATION);
    }
}