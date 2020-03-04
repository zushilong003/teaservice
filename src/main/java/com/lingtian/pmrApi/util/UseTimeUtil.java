package com.lingtian.pmrApi.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class UseTimeUtil {
    public static String computeUseTime(String time1, String time2) {
        log.info("进入时间方法");
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if (time2 == null || time2.equals("")) {
                Calendar calendar = Calendar.getInstance();
                startDate = dateFormat1.parse(time1);//转化为标准格式的date类型
//                    calendar.add(Calendar.HOUR_OF_DAY, -8);
                endDate = calendar.getTime();


            } else {
                startDate = dateFormat1.parse(time1);
                endDate = dateFormat2.parse(time2);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Interval interval = new Interval(startDate.getTime(),endDate.getTime());
//        Period period = interval.toPeriod();
//        return period.getDays()+"天，"+period.getHours()+"小时，"+period.getMinutes()+"分钟";
        return getDatePoor(endDate, startDate);

    }

    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

}
