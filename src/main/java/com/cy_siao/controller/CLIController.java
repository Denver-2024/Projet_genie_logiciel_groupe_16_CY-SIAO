package com.cy_siao.controller;

import com.cy_siao.view.CLIView;

import java.sql.SQLException;
import java.util.Scanner;

public class CLIController {
    private PersonController personController;
    private StayController stayController;
    private CLIView view = new CLIView();

    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
    }

    public void start() throws SQLException {
        int option;
        do {

            option = view.showMainMenu();

            switch (option) {
                case 1:
                    personController.start(view);
                    break;
                case 2:
                    stayController.start(view);
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
