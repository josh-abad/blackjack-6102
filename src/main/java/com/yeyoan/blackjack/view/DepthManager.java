package com.yeyoan.blackjack.view;

public class DepthManager {

    public static int LOW = 5;
    public static int HIGH = 30;
    
    public static String createDepth(int radius) {
        return "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), "
            + radius + ", 0, 0, 1);";
    }
}