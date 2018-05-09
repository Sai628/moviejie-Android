package com.sai628.moviejie.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Sai
 * @ClassName: DateUtil
 * @Description: 日期转换处理工具类
 * @date 2013-9-28 下午7:14:04
 * <pre>
 * 从 String/Long 变为 String 用 transformXX（表示转换）
 * 从 String 变为 Date 用 parseXX（表示解析）
 * 从 Date 变为 String 用 formatXX（表示格式化）
 * </pre>
 */
public class DateUtil
{
    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;


    /**
     * 将时间戳格式的时间转换为 "yyyy-MM-dd HH:mm:ss" 格式
     *
     * @param timestampInSeconds 待转换的时间戳,精度为毫秒
     * @return String 成功转换时返回转换后的时间字符串，否则返回 null
     */
    public static String toDateTimeString(long timestampInSeconds)
    {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");

        try
        {
            Date date = new Date(timestampInSeconds * 1000);

            return sdf.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取日期对象Calendar的年月日，得到型如："yyyy-MM-dd HH:mm:ss" 格式的字符串
     *
     * @param calendar 待处理的日期
     * @return String 型如："yyyy-MM-dd HH:mm:ss" 格式的字符串
     */
    public static String toDateTimeString(Calendar calendar)
    {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");

        return format.format(calendar.getTime());
    }


    /**
     * 获取日期对象Calendar的年月日，得到型如："yyyy-MM-dd" 格式的字符串
     *
     * @param calendar 待处理的日期
     * @return String 型如："yyyy-MM-dd" 格式的字符串
     */
    public static String toDateString(Calendar calendar)
    {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }


    /**
     * 获取日期对象Calendar的时分秒，得到型如："HH:mm:ss" 格式的字符串
     *
     * @param calendar 日期参数
     * @return String 型如："HH:mm:ss" 格式的字符串
     */
    public static String toTimeString(Calendar calendar)
    {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("HH:mm:ss");

        return format.format(calendar.getTime());
    }


    /**
     * 将时间戳转换为Calendar对象
     *
     * @param timestampInSeconds 待转换的时间字符串，为时间戳
     * @return Calendar 成功转换时返回相应的日期处理，否则返回 null
     */
    public static Calendar toCalendar(long timestampInSeconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampInSeconds * 1000);

        return calendar;
    }


    /**
     * 将 "yyyy-MM-dd HH:mm:ss" 格式的字符串转换为Calendar对象
     *
     * @param dateTime 待处理的字符串
     * @return Calendar 成功转换时返回相应的日期处理，否则返回 null
     */
    public static Calendar toCalendar(String dateTime)
    {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");

        try
        {
            Date date = format.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            return calendar;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将日期时间的字符串转换为Calendar对象
     *
     * @param date 日期字符串，型如："yyyy-MM-dd"
     * @param time 时间字符串，型如："HH:mm:ss"
     * @return Calendar 成功转换时返回相应的日期处理，否则返回 null
     */
    public static Calendar toCalendar(String date, String time)
    {
        if (date != null && time != null)
        {
            return toCalendar(date + " " + time);
        }

        return null;
    }


    /**
     * 将 型如 "yyyy-MM-dd"的日期与型如 "HH:mm:ss" 的时间解析为Date类型
     *
     * @param date 待解析的日期字符串
     * @param time 待解析的时间字符串
     * @return Date 成功解析时返回 Date 对象，否则返回 null
     */
    public static Date toDate(String date, String time)
    {
        if (date != null && time != null)
        {
            return toDate(date + " " + time);
        }

        return null;
    }


    /**
     * 将 "yyyy-MM-dd HH:mm:ss" 解析为日期类型
     *
     * @param dateTime 待解析的时间字符串
     * @return Date 成功解析时返回 Date 对象，否则返回 null
     */
    public static Date toDate(String dateTime)
    {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");

        try
        {
            return sdf.parse(dateTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将时间戳转换为用于显示的格式(单位为秒)
     *
     * @param timestampInSeconds
     * @return String 一般型如"MM-dd HH:mm"的格式
     */
    public static String toStringForShow(long timestampInSeconds)
    {
        String timeStr;
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        Date date = new Date(timestampInSeconds * 1000);
        Date currentDate = new Date();
        long durationTime = currentDate.getTime() - date.getTime();

        if (durationTime <= 10 * ONE_MINUTE_MILLIONS) // 十分钟内
        {
            timeStr = "刚刚";
        }
        else if (durationTime < ONE_HOUR_MILLIONS) // 一时内
        {
            timeStr = (durationTime / ONE_MINUTE_MILLIONS) + "分钟前";
        }
        else if (isSameYear(date, currentDate))
        {
            int dayStatus = calculateDayStatus(date, currentDate);
            if (dayStatus == 0) // 今天
            {
                timeStr = (durationTime / ONE_HOUR_MILLIONS) + "小时前";
            }
            else if (dayStatus == -1) // 昨天
            {
                sdf.applyPattern("HH:mm");
                timeStr = "昨天 " + sdf.format(date);
            }
            else
            {
                sdf.applyPattern("MM-dd HH:mm");
                timeStr = sdf.format(date);
            }
        }
        else
        {
            sdf.applyPattern("yyyy-MM-dd HH:mm");
            timeStr = sdf.format(date);
        }

        return timeStr;
    }


    /**
     * 将时间戳转换为用于显示的格式(用于进度日期中)
     *
     * @param timestampInSeconds
     * @return String
     */
    public static String toStringForSchedule(long timestampInSeconds)
    {
        Date date = new Date(timestampInSeconds * 1000);
        Date curDate = new Date();
        if (isSameYear(date, curDate))
        {
            return DateFormat.format("MM-dd", date).toString();
        }
        else
        {
            String dateString = DateFormat.format("yyyy-MM-dd", date).toString();
            return dateString.substring(5) + "\n" + dateString.substring(0, 4);
        }
    }


    /**
     * 将时间戳格式的时间转换为 "yyyy-MM-dd HH:mm" 格式
     *
     * @param timeStamp 待转换的时间戳,精度为毫秒
     * @return String 成功转换时返回转换后的时间字符串，否则返回 null
     */
    public static String toDateHMTimeString(long timeStamp)
    {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm");

        try
        {
            Date date = new Date(timeStamp);

            return sdf.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取两个日期之间相隔的月份数
     *
     * @param c1 日期1
     * @param c2 日期2
     * @return int 日期1与日期2之间相隔的月份数。若日期1在日期2之后，则返回0
     */
    public static int monthsBetween(Calendar c1, Calendar c2)
    {
        if (c1.after(c2))
        {
            return 0;
        }
        else
        {
            int year1 = c1.get(Calendar.YEAR);
            int year2 = c2.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH);
            int month2 = c2.get(Calendar.MONTH);

            return (month2 - month1) + (year2 - year1) * 12;
        }
    }


    /**
     * 获取两个日期之间相隔的分钟数
     *
     * @param c1 日期1
     * @param c2 日期2
     * @return int 日期1与日期2之间相隔的分钟数。若日期1在日期2之后，则返回0
     */
    public static int minutesBetween(Calendar c1, Calendar c2)
    {
        if (c1.after(c2))
        {
            return 0;
        }
        else
        {
            int days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
            int y2 = c2.get(Calendar.YEAR);
            if (c1.get(Calendar.YEAR) != y2)
            {
                c1 = (Calendar) c1.clone();
                do
                {
                    days += c1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
                    c1.add(Calendar.YEAR, 1);
                } while (c1.get(Calendar.YEAR) != y2);
            }

            int hours = days * 24 + c2.get(Calendar.HOUR_OF_DAY) - c1.get(Calendar.HOUR_OF_DAY);
            return hours * 60 + c2.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE);
        }
    }


    /**
     * 获取两个日期之间相隔的天数
     *
     * @param c1 日期1
     * @param c2 日期2
     * @return int 日期1与日期2之间相隔的天数。若日期1在日期2之后，则返回0
     */
    public static int daysBetween(Calendar c1, Calendar c2)
    {
        if (c1.after(c2))
        {
            return 0;
        }
        else
        {
            int days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
            int y2 = c2.get(Calendar.YEAR);
            if (c1.get(Calendar.YEAR) != y2)
            {
                c1 = (Calendar) c1.clone();
                do
                {
                    days += c1.getActualMaximum(Calendar.DAY_OF_YEAR); // 得到当年的实际天数
                    c1.add(Calendar.YEAR, 1);
                } while (c1.get(Calendar.YEAR) != y2);
            }

            return days;
        }
    }


    /**
     * 判断俩日期是否为同一年
     *
     * @param targetTime
     * @param compareTime
     * @return boolean
     */
    public static boolean isSameYear(Date targetTime, Date compareTime)
    {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }


    /**
     * 判断两个日期是否为同一年的同一个月
     *
     * @param c1 日期1
     * @param c2 日期2
     * @return boolean 若为同一年的同一个月返回true，否则返回false
     */
    public static boolean isSameMonth(Calendar c1, Calendar c2)
    {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }


    /**
     * 判断两个日期是否为同一天
     *
     * @param c1 日期1
     * @param c2 日期2
     * @return boolean 若为同一天返回true，否则返回false
     */
    public static boolean isSameDay(Calendar c1, Calendar c2)
    {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE));
    }


    /**
     * 判断结束日期是否比开始日期早
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return boolean 结束日期比开始日期早时返回true，否则返回false
     */
    public static boolean isBefore(Calendar start, Calendar end)
    {
        int ho1 = start.get(Calendar.HOUR_OF_DAY);
        int mi1 = start.get(Calendar.MINUTE);
        int ho2 = end.get(Calendar.HOUR_OF_DAY);
        int mi2 = end.get(Calendar.MINUTE);

        if (isSameDay(start, end))
        {
            if (ho1 > ho2)
            {
                return true;
            }
            else if (ho1 == ho2 && mi1 > mi2)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return start.after(end);
        }
    }


    /**
     * 判断日期date与时间time对应的时间点是否为已过去的时间
     *
     * @param date 日期,要求型如:"yyyy-MM-dd"
     * @param time 时间,要求型如:"HH:mm:ss"
     * @return boolean 为过去的时间返回true, 否则返回false.
     */
    public static boolean isPassTime(String date, String time)
    {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");

        try
        {
            Date srcDate = sdf.parse(date + " " + time);

            if (srcDate.getTime() < Calendar.getInstance().getTimeInMillis())
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }


    public static long getCurrentTimeInSeconds()
    {
        return (new Date().getTime() / 1000);
    }


    /**
     * 计算俩日期相差的天数(俩日期需为同一年)
     *
     * @param targetTime
     * @param compareTime
     * @return int 若为负数,则targetTime比compareTime日期要早.
     */
    public static int calculateDayStatus(Date targetTime, Date compareTime)
    {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }
}
