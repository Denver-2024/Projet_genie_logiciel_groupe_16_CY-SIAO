package com.cy_siao.view;

import java.time.LocalDate;
import java.util.Scanner;

public class CLIView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("Welcome to the CY SIAO");
        System.out.println("Here you can manage the siao. Does it cool?");
        System.out.println("Good luck!");
        System.out.println("Please select an option:");
        System.out.println("1. Manage a person");
        System.out.println("2. Manage a stay");
        System.out.println("3. Manage a bed");
        System.out.println("4. Manage a room");
        System.out.println("0. Exit");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public int showPersonMenu() {
        System.out.println("Welcome to the person management");
        System.out.println("Please select an option:");
        System.out.println("1. Show all persons");
        System.out.println("2. add new person");
        System.out.println("3. update person");
        System.out.println("4. delete person");
        System.out.println("0. Return");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public int showBedMenu() {
        System.out.println("Welcome to the bed management");
        System.out.println("Please select an option:");
        System.out.println("1. Show all beds");
        System.out.println("2. add new bed");
        System.out.println("3. update bed");
        System.out.println("4. delete bed");
        System.out.println("0. Return");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public int showRoomMenu() {
        System.out.println("Welcome to the room management");
        System.out.println("Please select an option:");
        System.out.println("1. Show all rooms");
        System.out.println("2. add new room");
        System.out.println("3. update room");
        System.out.println("4. delete room");
        System.out.println("0. Return");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }


    public String askString(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    public int askInt(String label) {
        System.out.print(label);
        int result = scanner.nextInt();
        scanner.nextLine();
    return result;
}

    public LocalDate askDate(String label) {
        System.out.print(label);
        return LocalDate.parse(scanner.nextLine());
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.err.println("Erreur : " + message);
    }

    public void close() {
        scanner.close();
    }
}