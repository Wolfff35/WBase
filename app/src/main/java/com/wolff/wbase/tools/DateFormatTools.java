package com.wolff.wbase.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wolff on 30.08.2017.
 */

public class DateFormatTools {
    public  static final String DATE_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_VID = "dd-MM-yyyy";
    public static final String DATE_FORMAT_VID_FULL = "dd-MM-yyyy HH:mm:ss";
    //public static final String DATE_FORMAT_SAVE = "yyyy-MM-dd-HH-mm-ss";
    public static final String DATE_FORMAT_SAVE = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DELETE = "%Y.%m.%d %H:%M:%S";

    public Date dateFromString(String strDate, String strFormat){
        //2017-02-02T15:30:00
        if(strDate==null){
            return null;
        }
        if(strDate.equalsIgnoreCase("")|strDate.isEmpty()){
            return null;
        }
        DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String dateToString(Date date,String strFormat){
        Date locDate;
        if(date==null){
            locDate = dateFromString("0001-01-01T00:00:00", DATE_FORMAT_STR);
        }else {
            locDate=date;
        }

        DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        return format.format(locDate);
    }
}
