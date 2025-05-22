package com.cy_siao.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class ViewManager {
    
    private static Stage primaryStage;

    public ViewManager(Stage stage) {
        primaryStage = stage;
        showMainMenu();
    }

    public void showMainMenu() {
        MainMenuView mainMenuView = new MainMenuView(this);
        primaryStage.setScene(new Scene(mainMenuView.getView()));
        configureStage();
    }

    public void showPersonView() {
        PersonView personView = new PersonView(this); // Nouvelle instance
        primaryStage.setScene(new Scene(personView.getView()));
        configureStage();
    }

    public void showFindStayView() {
        FindStayView findStayView = new FindStayView(this); // Nouvelle instance
        primaryStage.setScene(new Scene(findStayView.getView()));
        configureStage();
    }

    public void showBedView() {
        BedView bedView = new BedView(this);
        primaryStage.setScene(new Scene(bedView.getView()));
        configureStage();
    }

    public void showRoomView() {
        RoomView roomView = new RoomView(this);
        primaryStage.setScene(new Scene(roomView.getView()));
        configureStage();
    }

    public void showStayView() {
        StayView stayView = new StayView(this);
        primaryStage.setScene(new Scene(stayView.getView()));
        configureStage();
    }

    private void configureStage() {
        primaryStage.setTitle("CY SIAO Management");
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
    