package com.cy_siao.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import com.cy_siao.model.person.Person;
import com.cy_siao.view.MainMenuView;
import com.cy_siao.view.PersonView;
import com.cy_siao.view.BedView;
import com.cy_siao.view.RoomView;
import com.cy_siao.view.StayView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

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

    public void showDashboardView(){
        DashboardView dashboardView = new DashboardView(this);
        primaryStage.setScene(new Scene(dashboardView.getView()));
        configureStage();
    }
    private void configureStage() {
        primaryStage.setTitle("CY SIAO Manager");
        //Relative path from resources for  adding the icon to our software
        InputStream stream = getClass().getResourceAsStream("/Images/Projet_SIAO.png");
        try {
            if (stream != null) {
                primaryStage.getIcons().add(new Image(stream));
            }
        } catch (Exception e) {
            System.err.println("Error in loading image: " + e.getMessage());
        }
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
    