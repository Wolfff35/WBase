package com.wolff.wbase.model.abs;

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

public abstract class AWObject_saver {

    public abstract boolean addNewItem();

    protected abstract String getDataToPost();

    protected  String formatXmlHeader(Context context,String sObjectType){
        InputStream is_header = context.getResources().openRawResource(R.raw.post_query_task_header);
        DateFormatTools dateFormat = new DateFormatTools();
        StringConvertTools convert = new StringConvertTools();
        String s_currentDate = dateFormat.dateToString(new Date(),DATE_FORMAT_STR);
        return String.format(convert.stringFromInputStream(is_header),sObjectType,s_currentDate);
    }

    protected abstract String formatXmlBody();

    protected String formatXmlFooter(Context context){
        InputStream is_footer = context.getResources().openRawResource(R.raw.post_query_task_footer);
        StringConvertTools convert = new StringConvertTools();
        return convert.stringFromInputStream(is_footer);
    }
}
