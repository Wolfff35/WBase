package com.wolff.wbase.datalab;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.wolff.wbase.tools.PreferencesTools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wolff on 17.08.2017.
 */

public class OnlineConnector {
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    public static final String CONNECTION_TYPE_GET = "GET";
    public static final String CONNECTION_TYPE_PATCH = "PATCH";
    public static final String CONNECTION_TYPE_POST = "POST";

    public HttpURLConnection getConnection(Context context, String typeConnection, String url_s) throws IOException {
            URL url = new URL(url_s);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String authString = getAuthorization1CBase(context);
            connection.setRequestProperty("Authorization","Basic "+authString);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestMethod(typeConnection);
            connection.setDoInput(true);
            return connection;
    }
    private String getAuthorization1CBase(Context context){
        String authStr;
        if(PreferencesTools.IS_DEBUG){
            authStr= "wolf:1";
        }else {
            PreferencesTools pref = new PreferencesTools();
            authStr= pref.getStringPreference(context, PreferencesTools.PREFERENCE_BASE_LOGIN)
                    + ":"
                    + pref.getStringPreference(context, PreferencesTools.PREFERENCE_BASE_PASSWORD);
        }
        return new String(Base64.encode(authStr.getBytes(),0));
    }
    private String getBaseUrl(Context context){
        if (PreferencesTools.IS_DEBUG){
            return "http://13.10.12.10/v83_zadacha/odata/standard.odata/";
        }
        PreferencesTools pref = new PreferencesTools();
        return "http://"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_SERVER_NAME)
                +"/"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_NAME)
                +"/odata/standard.odata/";

    }


    public String getStringUrl(Context context,String typeConnection, String catalog,String guid){
        String url_s=null;
        switch (typeConnection){
            case CONNECTION_TYPE_GET: {
                String selection="";
                if(guid!=null&&guid!=""){
                    selection = "(guid'"+guid+"')";
                }
                url_s= Uri.parse(getBaseUrl(context) + catalog+selection +"/")
                        .buildUpon()
                        .appendQueryParameter("$format", "json")
                        .build()
                        .toString()
                        +getFiltersForQuery(context,catalog);
            break;
            }

            case CONNECTION_TYPE_PATCH:{
                url_s= Uri.parse(getBaseUrl(context) + catalog+"(guid'"+guid+"')"  + "/")
                        .buildUpon()
                        .appendQueryParameter("$format", "json")
                        .build()
                        .toString();
                break;
            }
            case CONNECTION_TYPE_POST:{
                url_s= Uri.parse(getBaseUrl(context) + catalog)
                        .buildUpon()
                        .build()
                        .toString();
                break;
            }
        }
        return url_s;
    }

    private String getFiltersForQuery(Context context, String catalog){
        return "";
    }

}
