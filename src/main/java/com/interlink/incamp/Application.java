package com.interlink.incamp;

import java.time.LocalDateTime;

public class Application {

    public static void main(String[] args) {
        int monthValue = LocalDateTime.now().getMonthValue();
        if (args.length == 1) {
            monthValue = Integer.parseInt(args[0]);
        }

        try {
            MonthCalendar simpleCalendar = new MonthCalendar(monthValue);
            System.out.println(simpleCalendar.toString());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument provided");
        }
    }
}
