package com.cy_siao.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import com.cy_siao.model.person.Person;
import com.cy_siao.view.MainMenuView;
import com.cy_siao.view.PersonView;
import javafx.stage.Stage;

public class ViewManager {
    
    private static Stage primaryStage;
    private MainMenuView mainMenuView;
    private PersonView personView;

    public ViewManager(Stage stage) {
        primaryStage = stage;
        initializeViews();
        showMainMenu();
    }

    private void initializeViews() {
        this.mainMenuView = new MainMenuView(this); // Passer this au constructeur
        this.personView = new PersonView(); 
    }

    public void showMainMenu() {
        primaryStage.setScene(new Scene(mainMenuView.getView()));
    }

    public void showPersonView() {
        primaryStage.setScene(new Scene(personView.getView()));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
