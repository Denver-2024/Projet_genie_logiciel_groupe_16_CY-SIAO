package com.cy_siao.controller.cli;

import com.cy_siao.view.CLIView;

import java.sql.SQLException;

public class CLIController {
    private PersonController personController;
    private StayController stayController;
    private BedController bedController;
    private RoomController roomController;
    private CLIView view = new CLIView();

    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
        this.bedController = new BedController();
        this.roomController = new RoomController();
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
                    //view.showError("Not yet implemented");
                    break;
                case 3:
                    bedController.start(view);
                    break;
                case 4:
                    roomController.start(view);
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
