package com.cy_siao;

import java.sql.SQLException;

import com.cy_siao.controller.CLIController;
import com.cy_siao.GuiApp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        CLIController controller = new CLIController();
        GuiApp guiApp = new GuiApp();
        guiApp.start(args);
        //controller.start();
    }
}
