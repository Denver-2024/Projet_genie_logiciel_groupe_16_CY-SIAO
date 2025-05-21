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
import javafx.stage.Stage;

public class ViewManager {
    
    private static Stage primaryStage;
    private MainMenuView mainMenuView;
    private PersonView personView;
    private BedView bedView;
    private RoomView roomView;
    private StayView stayView;

    public ViewManager(Stage stage) {
        primaryStage = stage;
        initializeViews();
        showMainMenu();
    }

    private void initializeViews() {
        this.mainMenuView = new MainMenuView(this);
        this.personView = new PersonView(this);
        this.bedView = new BedView(this);
        this.roomView = new RoomView(this);
        this.stayView = new StayView(this);
    }

    public void showMainMenu() {
        primaryStage.setScene(new Scene(mainMenuView.getView()));
    }

    public void showPersonView() {
        primaryStage.setScene(new Scene(personView.getView()));
    }

    public void showBedView() {
        primaryStage.setScene(new Scene(bedView.getView()));
    }

    public void showRoomView() {
        primaryStage.setScene(new Scene(roomView.getView()));
    }

    public void showStayView() {
        primaryStage.setScene(new Scene(stayView.getView()));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
    