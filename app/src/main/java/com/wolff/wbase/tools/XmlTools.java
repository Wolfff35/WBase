package com.wolff.wbase.tools;

import android.content.Context;

import com.wolff.wbase.R;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.StringConvertTools;

import java.io.InputStream;
import java.util.Date;

import static com.wolff.wbase.tools.DateFormatTools.DATE_FORMAT_STR;


/**
 * Created by wolff on 30.08.2017.
 */

public class XmlTools {

    public static String formatXmlHeader(Context context,String sObjectType){
        InputStream is_header = context.getResources().openRawResource(R.raw.post_query_task_header);
        DateFormatTools dateFormat = new DateFormatTools();
        StringConvertTools convert = new StringConvertTools();
        String s_currentDate = dateFormat.dateToString(new Date(),DATE_FORMAT_STR);
        String s = String.format(convert.stringFromInputStream(is_header),sObjectType,s_currentDate);
        Debug.Log("HEADER",""+s);
        return s;
    }


    public static String formatXmlFooter(Context context){
        InputStream is_footer = context.getResources().openRawResource(R.raw.post_query_task_footer);
        StringConvertTools convert = new StringConvertTools();
        String s = convert.stringFromInputStream(is_footer);
        Debug.Log("HEADER",""+s);
        return s;
    }

}
