package com.yeyoan.blackjack;

import com.yeyoan.blackjack.controller.Controller;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.view.Sandbox;

public class Blackjack {
    
    public static void main(String[] args) {
        Model model = new Model();
        Sandbox view = new Sandbox();
        Controller controller = new Controller(model, view);
        controller.run();
    }
}