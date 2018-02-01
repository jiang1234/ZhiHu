package com.ali.zhihu.ui.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.media.CamcorderProfile.get;

/**
 * Created by Administrator on 2018/2/1.
 */

public class DateUtil {
    public static String TAG = "DateChange";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM月dd日");
//date前days天的日期
    public static String Before(int days,String date){
        try {
            Date dateTransform = dateFormat.parse(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dateTransform);
            rightNow.add(Calendar.DAY_OF_YEAR,-days);
            Log.i(TAG,dateFormat.format(rightNow.getTime()));
            return dateFormat.format(rightNow.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
//判断是否为当前时间
    public static boolean isSystemDate(String date){
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date systemDate = new Date(System.currentTimeMillis());
        String systemDateString = dateFormat.format(systemDate);
        return date.equals(systemDateString);
    }
//改变日期显示格式
    public static String changeFormat(String date){
        try {
            Date dateTransform = dateFormat.parse(date);
            return dateFormat1.format(dateTransform);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
