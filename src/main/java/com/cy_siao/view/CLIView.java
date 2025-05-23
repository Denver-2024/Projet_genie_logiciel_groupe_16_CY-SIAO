package com.cy_siao.view;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * CLI view class for user interaction via the command line.
 * Provides menus and input prompts for managing persons, stays, beds, and rooms.
 */
public class CLIView {
    //Scanner for reading user input from command line
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu and gets the user's choice.
     *
     * @return The selected menu option as an integer
     */
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

    /**
     * Displays the person management menu options.
     *
     * @return The selected menu option as an integer
     */
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

    /**
     * Displays the bed management menu options.
     *
     * @return The selected menu option as an integer
     */
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

    /**
     * Displays the room management menu options.
     *
     * @return The selected menu option as an integer
     */
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

    /**
     * Displays the stay management menu options.
     *
     * @return The selected menu option as an integer
     */
    public int showStayMenu() {
        System.out.println("Welcome to the stay management");
        System.out.println("Please select an option:");
        System.out.println("1. Assign a stay");
        System.out.println("2. Remove a stay");
        System.out.println("3. free a bed");
        System.out.println("4. list all stays");
        System.out.println("0. Return");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    /**
     * Prompts for and reads a string input from the user.
     *
     * @param label The prompt message to display
     * @return The string entered by the user
     */
    public String askString(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    /**
     * Prompts for and reads an integer input from the user.
     *
     * @param label The prompt message to display
     * @return The integer entered by the user
     */
    public int askInt(String label) {
        System.out.print(label);
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    /**
     * Prompts for and reads a date input from the user.
     *
     * @param label The prompt message to display
     * @return The LocalDate parsed from user input
     */
    public LocalDate askDate(String label) {
        System.out.print(label);
        return LocalDate.parse(scanner.nextLine());
    }

    /**
     * Displays an informational message to the user.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.err.println("Erreur : " + message);
    }

    /**
     * Closes the scanner and releases system resources.
     */
    public void close() {
        scanner.close();
    }
}