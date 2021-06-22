package com.example.newsapp;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateFormat {

    public static String dateTime(String dataDate) {

        String formatedTime = null;
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                formatedTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                formatedTime = minute + " Minutes " + suffix;
            } else if (hour < 24) {
                formatedTime = hour + " Hours " + suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    formatedTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    formatedTime = (day / 30) + " Months " + suffix;
                } else {
                    formatedTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                formatedTime = day + " Days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return formatedTime;
    }


    public static String simpleDateFormat(String oldstringDate) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }


}
