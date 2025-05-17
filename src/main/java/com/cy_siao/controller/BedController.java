package com.cy_siao.controller;

import com.cy_siao.model.Bed;
import com.cy_siao.model.Room;
import com.cy_siao.service.BedService;
import com.cy_siao.service.RoomService;
import com.cy_siao.view.CLIView;

import java.util.List;


public class BedController {

    private BedService bedService;
    private RoomService roomService;
    private CLIView view;

    public BedController(){
        this.bedService = new BedService();
        this.roomService = new RoomService();
    }

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

    private void updateBed(){
        int idBed = view.askInt("Give id bed which you want to change room :");
        int idRoom = view.askInt("Give id room which you want to change room :");
        roomService.removeBedFromRoom(roomService.getRoomById(bedService.getBedById(idBed).getIdRoom()), bedService.getBedById(idBed));
        roomService.addBedToRoom(roomService.getRoomById(idRoom), bedService.getBedById(idBed));
    }

    private void createBed(){
        int idRoom = view.askInt("Give id Room where you want to place bed :");
        bedService.createBed(new Bed(idRoom)); // check id room
    }

    private void deleteBed(){
        int id = Integer.parseInt(view.askString("Enter perbedson id: "));
        Bed bed = bedService.getBedById(id);
        if (bed!=null){
            bedService.deleteBed(id);
        }else{
            view.showError("No bed found with id: "+id);
        }
    }
}
