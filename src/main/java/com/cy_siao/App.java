package com.cy_siao;

import java.sql.SQLException;
import com.cy_siao.controller.cli.CLIController;

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
