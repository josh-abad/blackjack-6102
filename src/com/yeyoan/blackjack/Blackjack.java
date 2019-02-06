package com.yeyoan.blackjack;

import com.yeyoan.blackjack.controller.Controller;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.view.View;

public class Blackjack {
    
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }
}