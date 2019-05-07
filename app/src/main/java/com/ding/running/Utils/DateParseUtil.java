package com.ding.running.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author Leoren
 * @Date 2018/6/27 20:03
 *
 * 时间的格式化工具类
 */
public class DateParseUtil {

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String parseData(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static String parseDataHMS(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static String parseDataYMD(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return df.format(date);
    }

    public static String getChattime(Date date){
        DateFormat he = DateFormat.getTimeInstance();
        return he.format(date).toString();
    }


    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date currentTime) {
        //Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间的月份
     * @return
     */
    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getIntDate(String str){
        String[] strings = str.split(":");
        int hour = Integer.parseInt(strings[0]);
        int min = Integer.parseInt(strings[1]);
        int second = Integer.parseInt(strings[2]);
        return hour*3600 + min * 60 + second;
    }


    public static String GetUsedTime(int usedTime) {
        int hour = usedTime / 60;
        int min = usedTime % 60;
        return new StringBuilder().append(hour).append("时").append(min).append("分").toString();

    }

    /**
     * 获取明天的时期  xxxx-xx-xx格式
     * @return
     */
    public static String GetTomorrow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        String time = sdf.format(c.getTime());
        return time;
    }
}
