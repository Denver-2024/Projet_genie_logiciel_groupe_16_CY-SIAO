package com.cy_siao.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GuiView extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainMenu();
    }

    public void showMainMenu() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Welcome to the CY SIAO");
        Label subtitle = new Label("Here you can manage the SIAO. Does it cool?\nGood luck!");
        Button personButton = new Button("Manage a person");
        Button stayButton = new Button("Manage a stay");
        Button exitButton = new Button("Exit");

        personButton.setOnAction(e -> showPersonMenu());
        stayButton.setOnAction(e -> showMessage("Stay management coming soon..."));
        exitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(title, subtitle, personButton, stayButton, exitButton);

        Scene scene = new Scene(layout, 350, 250);
        primaryStage.setTitle("CY SIAO - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPersonMenu() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Person Management");
        Button showAll = new Button("Show all persons");
        Button add = new Button("Add new person");
        Button update = new Button("Update person");
        Button delete = new Button("Delete person");
        Button back = new Button("Back");

        // Actions à compléter selon ton contrôleur métier
        showAll.setOnAction(e -> showMessage("Listing all persons..."));
        add.setOnAction(e -> {
            String name = askString("Enter name:");
            int age = askInt("Enter age:");
            showMessage("Person added: " + name + " (" + age + ")");
        });

        update.setOnAction(e -> showMessage("Update logic not implemented."));
        delete.setOnAction(e -> showMessage("Delete logic not implemented."));
        back.setOnAction(e -> showMainMenu());

        layout.getChildren().addAll(label, showAll, add, update, delete, back);

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setScene(scene);
    }

    public String askString(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse("");
    }

    public int askInt(String prompt) {
        String input = askString(prompt);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
            return askInt(prompt);
        }
    }

    public LocalDate askDate(String prompt) {
        String input = askString(prompt + " (yyyy-MM-dd)");
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            showError("Invalid date format.");
            return askDate(prompt);
        }
    }

    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}