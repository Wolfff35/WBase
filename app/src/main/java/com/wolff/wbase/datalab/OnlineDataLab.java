package com.wolff.wbase.datalab;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Base64;
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
private String getAuthorization1CBase(){
    return null;
}
    /*public HttpURLConnection getConnection(Context context,String type,String catalog,String guidCurrentUser){
    try {
        String url_s;
        if(type.equalsIgnoreCase("GET")) {
            url_s = getBaseUrl(context) + catalog + "/?$format=json"+getFiltersForQuery(context,catalog,guidCurrentUser);
        }else if(type.equalsIgnoreCase("PATCH")) {
            url_s = Uri.parse(getBaseUrl(context) + catalog + "/").buildUpon().appendQueryParameter("$format", "json").build().toString();
        }else if(type.equalsIgnoreCase("POST")){
            url_s = Uri.parse(getBaseUrl(context) + catalog).buildUpon().build().toString();
        }else {
            url_s=null;
        }
        URL url = new URL(url_s);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        String authString = getAuthorization1C(context);
        String authStringEnc = new String(Base64.encode(authString.getBytes(),0));
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        Log.e("CONNECTION","GOOD - "+url_s);

        return connection;
    } catch (MalformedURLException e) {
        Log.e("ERROR 1",""+e.getLocalizedMessage());
    } catch (IOException e) {
        Log.e("ERROR 2",""+e.getLocalizedMessage());
    }
    return null;
}*/

}
