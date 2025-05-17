package com.cy_siao.controller;

import com.cy_siao.service.StayService;
import com.cy_siao.view.CLIView;

import java.util.Scanner;

public class StayController {
    private CLIView view;
    private StayService stayService = new StayService();
    /*
    public StayController() {
        this.stayService = new StayService();
        this.view = new CLIView();
    }

    public void start(CLIView view) {
        this.view = view;
        int option;
        do{
            option = view.showStayMenu();
            switch (option) {
                case 1:
                    view.showMessage(personService.getAllPersons().toString());
                    break;
                case 2:
                    this.add();
                    break;
                case 3:
                    this.updatePerson();
                    break;
                case 4:
                    this.deletePerson();
            }
        }while(option!=0);
    }

     */
}