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


/**
 * Manages the different views of the application and handles stage configuration.
 */
public class ViewManager {
    
    private static Stage primaryStage; // Primary stage of the application

    /**
     * Constructs the ViewManager with the primary stage and shows the main menu.
     *
     * @param stage The primary stage of the application
     */
    public ViewManager(Stage stage) {
        primaryStage = stage;
        showMainMenu();
    }

    /**
     * Shows the main menu view.
     */
    public void showMainMenu() {
        MainMenuView mainMenuView = new MainMenuView(this);
        primaryStage.setScene(new Scene(mainMenuView.getView()));
        configureStage();
    }

    /**
     * Shows the person management view.
     */
    public void showPersonView() {
        PersonView personView = new PersonView(this); // Nouvelle instance
        primaryStage.setScene(new Scene(personView.getView()));
        configureStage();
    }

    /**
     * Shows the bed management view.
     */
    public void showBedView() {
        BedView bedView = new BedView(this);
        primaryStage.setScene(new Scene(bedView.getView()));
        configureStage();
    }

    /**
     * Shows the room management view.
     */
    public void showRoomView() {
        RoomView roomView = new RoomView(this);
        primaryStage.setScene(new Scene(roomView.getView()));
        configureStage();
    }

    /**
     * Shows the stay management view.
     */
    public void showStayView() {
        StayView stayView = new StayView(this);
        primaryStage.setScene(new Scene(stayView.getView()));
        configureStage();
    }

    /**
     * Shows the dashboard view.
     */
    public void showDashboardView(){
        DashboardView dashboardView = new DashboardView(this);
        primaryStage.setScene(new Scene(dashboardView.getView()));
        configureStage();
    }

    /**
     * Shows the planning view.
     */
    public void showPlanningView() {
        PlanningView planningView = new PlanningView(this);
        getPrimaryStage().setScene(new Scene(planningView.getView()));
    }

    /**
     * Configures the primary stage with title and icon.
     */
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

    /**
     * Gets the primary stage.
     *
     * @return The primary Stage instance
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
    