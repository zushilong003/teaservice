package com.lingtian.pmrApi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UTCSwitchUtil {
    public static String utcToLocal(String oldDate) {

        DateFormat dateFormat = null;
        Calendar calendar = null;
        try {
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat1.parse(oldDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 8);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return dateFormat.format(calendar.getTime());
    }

}
