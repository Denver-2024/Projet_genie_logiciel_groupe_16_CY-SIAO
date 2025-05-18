package com.cy_siao.controller;

import com.cy_siao.view.GuiView;

public class GUIController {
    
    private GuiView guiView;

    public GUIController(){
        guiView = new GuiView();
    }

    public void start(String[] args){
        GuiView.launch(GuiView.class, args);
    }

}
