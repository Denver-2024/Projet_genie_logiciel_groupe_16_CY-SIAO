package com.cy_siao.controller;

import com.cy_siao.model.Bed;
import com.cy_siao.model.Room;
import com.cy_siao.service.BedService;
import com.cy_siao.service.RoomService;
import com.cy_siao.view.CLIView;


import java.util.List;

public class RoomController {

    private BedService bedService;
    private RoomService roomService;
    private CLIView view;

    public RoomController(){
        this.bedService = new BedService();
        this.roomService = new RoomService();
    }

    public void start(CLIView view){
        int option;
        this.view=view;
        do{
            option=view.showRoomMenu();
            switch (option){
                case 1:
                    view.showMessage(bedService.getAllBeds().toString());
                    break;
                case 2:
                    this.createRoom();
                    break;
                case 3:
                    this.updateRoom();
                    break;
                case 4:
                    this.deleteRoom();
                    break;
            }
        } while(option!=0);
        
    }

    private void updateRoom(){
        int idRoom = view.askInt("Give id room which you want to edit:");
        String name = view.askString("Give new name of the room - or '' if you don't want to change");
        int nbBed = view.askInt("Give new number of bed max in the room - or 0 if you don't want to change");
        Room room = new Room(name, nbBed);
        if (name == ""){
            room.setName(roomService.getRoomById(idRoom).getName());
        }
        if (nbBed == 0){
            room.setNbBedsMax(roomService.getRoomById(idRoom).getNbBedsMax());
        }
        roomService.updateRoom(room);
    }

    private void createRoom(){
        String nameRoom = view.askString("Give name of the room");
        int nbBedsMax = view.askInt("Give number max of bed in the room");
        roomService.createRoom(new Room(nameRoom, nbBedsMax));
    }

    private void deleteRoom(){
        int id = Integer.parseInt(view.askString("Enter room id: "));
        Room room = roomService.getRoomById(id);
        if (room!=null){
            roomService.deleteRoom(id);
        }else{
            view.showError("No room found with id: "+id);
        }
    }

}
