package com.cy_siao.controller.cli;

import com.cy_siao.model.Bed;
import com.cy_siao.service.BedService;
import com.cy_siao.service.RoomService;
import com.cy_siao.view.CLIView;


/**
 * Controller class for managing bed-related operations in the CLI interface.
 * Handles user interactions for creating, updating, deleting, and listing beds.
 */
public class BedController {

    private BedService bedService; // Service for bed business logic
    private RoomService roomService; // Service for room business logic
    private CLIView view; // CLI view for user interaction

    /**
     * Default constructor initializing the bed and room services.
     */
    public BedController(){
        this.bedService = new BedService();
        this.roomService = new RoomService();
    }

    /**
     * Starts the bed management menu loop.
     *
     * @param view The CLI view instance for user interaction
     */
    public void start(CLIView view) {
        int option;
        this.view=view;
        do{
            option=view.showBedMenu();
            switch (option){
                case 1:
                    view.showMessage(bedService.getAllBeds().toString());
                    break;
                case 2:
                    this.createBed();
                    break;
                case 3:
                    this.updateBed();
                    break;
                case 4:
                    this.deleteBed();
                    break;
            }
        } while(option!=0);
    }

    /**
     * Updates the room assignment of a bed.
     */
    private void updateBed(){
        int idBed = view.askInt("Give id bed which you want to change room :");
        int idRoom = view.askInt("Give id room which you want to change room :");
        roomService.removeBedFromRoom(roomService.getRoomById(bedService.getBedById(idBed).getIdRoom()), bedService.getBedById(idBed));
        roomService.addBedToRoom(roomService.getRoomById(idRoom), bedService.getBedById(idBed));
    }

    /**
     * Creates a new bed in a specified room.
     */
    private void createBed(){
        int idRoom = view.askInt("Give id Room where you want to place bed :");
        bedService.createBed(new Bed(idRoom));
    }

    /**
     * Deletes a bed by its ID.
     */
    private void deleteBed(){
        int id = Integer.parseInt(view.askString("Enter bed id: "));
        Bed bed = bedService.getBedById(id);
        if (bed!=null){
            bedService.deleteBed(id);
        }else{
            view.showError("No bed found with id: "+id);
        }
    }
}
