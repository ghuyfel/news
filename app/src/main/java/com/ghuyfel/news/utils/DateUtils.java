package com.ghuyfel.news.utils;

import com.ghuyfel.news.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static String TAG = DateUtils.class.getSimpleName();

    //Since date parsing with pattern '/Date('SSSSSSSSSSSSSZZZZZ')/' didn't work, we had to manually hack into the string
    public static synchronized String getFormattedDate(String dateString) {
            SimpleDateFormat df = new SimpleDateFormat(Constants.PATTERN_APP_DATE);
            return df.format(getMilliseconds(dateString));
    }

    private static Date getMilliseconds(String apiDate) {
        String ms = apiDate.replace("/Date(","").replace("+0200)/","");
        long milliseconds = Long.valueOf(ms);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return cal.getTime();
    }

}
