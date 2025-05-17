package com.cy_siao.controller;

import com.cy_siao.view.CLIView;

import java.util.Scanner;

public class CLIController {
    private PersonController personController;
    private StayController stayController;
    private CLIView view = new CLIView();

    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
    }

    public void start() {
        int option;
        do {

            option = view.showMainMenu();

            switch (option) {
                case 1:
                    personController.start(view);
                    break;
                case 2:
                    //stayController.start(view);
                    view.showError("Not yet implemented");
                    break;
                case 0:
                    view.showMessage("Exiting...");
                    break;
                default:
                    view.showError("Invalid option");
            }
        }while(option !=0);

        view.close();
    }
}