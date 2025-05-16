package com.cy_siao.controller;

import java.util.Scanner;
import com.cy_siao.view.CliView;

public class CliController {
    
    private Scanner scanner;
    private CliView cliView;

    public void start() {

        boolean running = true;
        cliView.displayWelcomeMessage();
        while (running) {
            String choice = scanner.nextLine();

            if (choice == "exit"){
                running = false;
                break;
            }
        }
        scanner.close();

    }
}
