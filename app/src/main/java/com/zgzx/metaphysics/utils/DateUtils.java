package com.zgzx.metaphysics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间
 */
public class DateUtils {

    public static final String PATTERN_1 = "HH:mm MM/dd",
            PATTERN_2 = "yyyy-MM-dd",
            PATTERN_4 = "MM.dd",
            PATTERN_5 = "yyyy-MM-dd HH:mm",
            PATTERN_3 = "yyyy.MM.dd HH:mm:ss";

    private DateUtils() {

    }

    public static String getTime(int time, String pattern) {
//        Logger.i("date utils %d", time);
        return new SimpleDateFormat(pattern).format(new Date(time * 1000L));
    }

    public static String getTime(long time, String pattern) {
//        Logger.i("date utils %d", time);
        return new SimpleDateFormat(pattern).format(new Date(time * 1000L));
    }

    /**
     * 获取当月第几周
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static int getWeeksMonth(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_2);
        Date date = sdf.parse(str);

        java.util.Calendar calendar1 = java.util.Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        int weekOfMonth = calendar1.get(java.util.Calendar.WEEK_OF_MONTH);
        return weekOfMonth;
    }


    public static int getRealHour(int hour, int calendarType) {

        switch (calendarType) {
            case 1://农历
                return hour + 1;

            case 2://阳历
                int hourTime = 0;
                switch (hour) {
                    case 0:
                        hourTime = 23;
                        break;
                    case 1:
                        hourTime = 1;
                        break;
                    case 2:
                        hourTime = 3;
                        break;
                    case 3:
                        hourTime = 5;
                        break;
                    case 4:
                        hourTime = 7;
                        break;
                    case 5:
                        hourTime = 9;
                        break;
                    case 6:
                        hourTime = 11;
                        break;
                    case 7:
                        hourTime = 13;
                        break;
                    case 8:
                        hourTime = 15;
                        break;
                    case 9:
                        hourTime = 17;
                        break;
                    case 10:
                        hourTime = 19;
                        break;
                    case 11:
                        hourTime = 21;
                        break;

                }
                return hourTime;



        }
        return 1;
    }

    public static int getTimestamp() {
        long time = System.currentTimeMillis();
        return (int) (time / 1000);
    }

    /**
     * @param time
     * @return 根据秒数获取 00:00:00 格式的时间
     */
    public static String secToTime(int time) {
        String timeStr = "";
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static Calendar getCalendarViaTimestamp(long time) {

        String result1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time * 1000));
        System.out.println("10位数的时间戳（秒）--->Date:" + result1);
        Date date1 = new Date(time * 1000);   //对应的就是时间戳对应的Date

        return dataToCalendar(date1);

    }


    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
