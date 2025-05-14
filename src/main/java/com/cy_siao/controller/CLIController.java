package com.cy_siao.controller;

import java.util.Scanner;

public class CLIController {
    private PersonController personController;
    private StayController stayController;
    private Scanner scanner = new Scanner(System.in);

    public CLIController() {
        this.personController = new PersonController();
        this.stayController = new StayController();
    }

    public void start() {
        int option;
        do {
            System.out.println("Welcome to the CY SIAO");
            System.out.println("Here you can manage the siao. Does it cool?");
            System.out.println("Good luck!");
            System.out.println("Please select an option:");
            System.out.println("1. Manage a person");
            System.out.println("2. Manage a stay");
            System.out.println("0. Exit");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    personController.start(scanner);
                    break;
                case 2:
                    stayController.start(scanner);
                    break;
                case 0:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }while(option !=0);

    scanner.close();
    }
}
