package com.cy_siao.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.cy_siao.controller.GUIController;

public class GuiView {
    private Stage primaryStage;
    private GUIController controller;
    
    public GuiView(Stage primaryStage, GUIController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

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

    public String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse("");
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}