package com.wolff.wbase.datalab;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.wolff.wbase.tools.PreferencesTools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataLab {

//=================================================================================================
private HttpURLConnection getConnection(Context context, String typeConnection,String catalog){
    String url_s = getStringUrl(context,typeConnection,catalog);
    try {
        URL url = new URL(url_s);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        String authString = getAuthorization1CBase();
        connection.setRequestProperty("Authorization","Basic "+authString);
        return connection;
    } catch (IOException e) {
        Log.e("get connection","NO CONNECTION "+e.getLocalizedMessage());
        return null;
    }
}
private String getBaseUrl(Context context){
    PreferencesTools pref = new PreferencesTools();
    return "http://"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_SERVER_NAME)+"/"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_NAME)+"/odata/standard.odata/";

}
private String getFiltersForQuery(Context context, String catalog){

    return "";
}
private String getStringUrl(Context context,String typeConnection, String catalog){
    switch (typeConnection){
        case "GET": {
            return Uri.parse(getBaseUrl(context) + catalog +"/").buildUpon().appendQueryParameter("$format", "json").build().toString()+getFiltersForQuery(context,catalog);
            //return getBaseUrl(context) + catalog + "/?$format=json"+getFiltersForQuery(context,catalog);
        }
        case "PATCH":{
            return Uri.parse(getBaseUrl(context) + catalog + "/").buildUpon().appendQueryParameter("$format", "json").build().toString();
        }
        case "POST":{
            return Uri.parse(getBaseUrl(context) + catalog).buildUpon().build().toString();
        }
    }
    return null;
}
private String getAuthorization1CBase(Context context){
    PreferencesTools pref = new PreferencesTools();

    return pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_LOGIN)+":"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_PASSWORD);
}
}
