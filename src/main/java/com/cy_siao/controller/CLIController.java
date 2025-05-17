package com.cy_siao.controller;

import com.cy_siao.view.CLIView;

import java.util.Scanner;

public class CLIController {
    private PersonController personController;
    private StayController stayController;
    private BedController bedController;
    private CLIView view = new CLIView();

    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
        this.bedController = new BedController();
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
                case 3:
                    bedController.start(view);
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