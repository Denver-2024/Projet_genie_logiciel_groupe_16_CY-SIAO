package com.cy_siao.controller.cli;

import com.cy_siao.view.CLIView;

import java.sql.SQLException;

/**
 * CLIController manages the command-line interface interactions.
 * It delegates user actions to the appropriate controllers for persons, stays, beds, and rooms.
 */
public class CLIController {
    // Controllers for different entities
    private PersonController personController;
    private StayController stayController;
    private BedController bedController;
    private RoomController roomController;
    // CLI view for user interaction
    private CLIView view = new CLIView();

    /**
     * Initializes all controllers used by the CLI.
     */
    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
        this.bedController = new BedController();
        this.roomController = new RoomController();
    }

    /**
     * Starts the CLI main loop, displaying the menu and handling user input.
     * @throws SQLException if a database error occurs
     */
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
