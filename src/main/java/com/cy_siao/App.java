package com.cy_siao;

import com.cy_siao.controller.CLIController;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        CLIController controller = new CLIController();
        controller.start();
    }
}
