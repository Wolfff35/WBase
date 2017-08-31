package com.wolff.wbase.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wolff on 30.08.2017.
 */

public class StringConvertTools {
    public String stringFromInputStream(InputStream is){
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addFieldToXml(StringBuffer sb,String fieldName,String field){
        if(field!=null&&field!="") {
            sb.append("<d:" + fieldName + ">" + field + "</d:" + fieldName + ">");
        }
    }}
