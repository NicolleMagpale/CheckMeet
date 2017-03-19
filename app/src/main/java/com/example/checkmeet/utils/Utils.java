package com.example.checkmeet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by victo on 3/19/2017.
 */

public class Utils {

    public static String dateIntegerToString(int date_integer) {
        int hours = date_integer / 100;
        int minutes = date_integer % 100;

        String strdate24 = hours + ":" + minutes;

        SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

        try {
            Date date = originalFormat.parse(strdate24);

            return parseFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int dateStringToInteger(String date_string) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
        int time24 = 0;

        try {
            Date date = originalFormat.parse(date_string);

            String parsedDate = parseFormat.format(date);

            String[] tokens = parsedDate.split(":");
            int hours = Integer.parseInt(tokens[0]);
            int minutes = Integer.parseInt(tokens[1]);

            hours = hours * 100;
            time24 = hours + minutes;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time24;
    }
}
