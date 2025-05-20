package com.cy_siao.controller.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class GUIPersonController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // --- Navigation Methods ---

    public void goToAddPerson(ActionEvent event) throws IOException {
        switchScene(event, "/fxml/person/addPerson.fxml");
    }

    public void goToDeletePerson(ActionEvent event) throws IOException {
        switchScene(event, "/fxml/person/deletePerson.fxml");
    }

    public void goToUpdatePerson(ActionEvent event) throws IOException {
        switchScene(event, "/fxml/person/updatePerson.fxml");
    }

    public void goToPersonsList(ActionEvent event) throws IOException {
        switchScene(event, "/fxml/person/PersonsList.fxml");
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        switchScene(event, "/fxml/person/PersonManagement.fxml");
    }

    // --- Helper to switch scenes ---
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));

        // Trouver la scène depuis n'importe quelle source d'événement
        Stage stage = null;

        Object source = event.getSource();
        if (source instanceof Node node) {
            stage = (Stage) node.getScene().getWindow();
        } else {
            // Cas des MenuItem (pas des Nodes) → on remonte à la fenêtre active
            stage = (Stage) javafx.stage.Window.getWindows().stream()
                    .filter(Window::isShowing)
                    .findFirst()
                    .orElse(null);
        }

        if (stage != null) {
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Unable to get the stage from the event source.");
        }
    }


    // --- Sample Handlers with Dialogs ---

    public void handleAddPerson(ActionEvent event) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Add");
        confirmation.setHeaderText("Are you sure you want to add this person?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Do the add logic here (e.g. insert into database)
            showInformation("Person has been added successfully.");
        }
    }

    public void handleDeletePerson(ActionEvent event) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Are you sure you want to delete this person?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Do the delete logic here
            showInformation("Person has been deleted successfully.");
        }
    }

    public void handleUpdatePerson(ActionEvent event) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Update");
        confirmation.setHeaderText("Are you sure you want to update this person?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Do the update logic here
            showInformation("Person has been updated successfully.");
        }
    }

    // --- Utility to show alerts ---
    private void showInformation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
