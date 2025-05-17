package com.cy_siao.controller;

import com.cy_siao.view.CLIView;

import java.util.Scanner;

public class StayController {
    public void start(CLIView view) {
        System.out.println("Welcome to the stay management");
        System.out.println("Please select an option:");
        System.out.println("1. Show free beds");
        System.out.println("2. Show current stays");
        System.out.println("0. Return");
    }
}
