package com.cy_siao.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.cy_siao.controller.GUIController;

/**
 * GUI view class for displaying the graphical user interface.
 * Manages the primary stage and user interactions for main and person menus.
 */
public class GuiView {
    private Stage primaryStage; // Primary stage of the application
    private GUIController controller; // Controller handling GUI actions
    
    /**
     * Constructs the GuiView with the primary stage and controller.
     *
     * @param primaryStage The main application window
     * @param controller The GUI controller for handling events
     */
    public GuiView(Stage primaryStage, GUIController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    /**
     * Shows the main menu with options for managing persons and stays.
     */
    public void showMainMenu() {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label title = new Label("Welcome to the CY SIAO");
        Label subtitle = new Label("Here you can manage the SIAO. Does it cool?\nGood luck!");
        Button personButton = new Button("Manage a person");
        Button stayButton = new Button("Manage a stay");
        Button exitButton = new Button("Exit");

        personButton.setOnAction(e -> controller.handlePersonMenu());
        stayButton.setOnAction(e -> controller.showMessage("Stay management coming soon..."));
        exitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(title, subtitle, personButton, stayButton, exitButton);

        Scene scene = new Scene(layout, 350, 250);
        primaryStage.setTitle("CY SIAO - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the person management menu with options to show, add, update, or delete persons.
     */
    public void showPersonMenu() {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        Label label = new Label("Person Management");
        Button showAll = new Button("Show all persons");
        Button add = new Button("Add new person");
        Button update = new Button("Update person");
        Button delete = new Button("Delete person");
        Button back = new Button("Back");

        showAll.setOnAction(e -> controller.handleShowAllPersons());
        add.setOnAction(e -> controller.handleAddPerson());
        update.setOnAction(e -> controller.handleUpdatePerson());
        delete.setOnAction(e -> controller.handleDeletePerson());
        back.setOnAction(e -> controller.handleBackToMain());

        layout.getChildren().addAll(label, showAll, add, update, delete, back);

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setScene(scene);
    }

    /**
     * Shows an input dialog with a prompt and returns the user input.
     *
     * @param prompt The message to display in the input dialog
     * @return The user input as a string
     */
    public String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse("");
    }

    /**
     * Shows an alert dialog with a title, message, and alert type.
     *
     * @param title The title of the alert dialog
     * @param message The message to display
     * @param type The type of alert (e.g., INFORMATION, ERROR)
     */
    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
