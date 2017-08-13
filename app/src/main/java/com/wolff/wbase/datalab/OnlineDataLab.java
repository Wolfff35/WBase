package com.wolff.wbase.datalab;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataLab {

    private static OnlineDataLab sOnlineDataLab;
    private Context mContext;
    private HttpURLConnection mConnection;

    public static OnlineDataLab get(Context context){
        if(sOnlineDataLab==null){
            sOnlineDataLab = new OnlineDataLab(context);
        }
        return sOnlineDataLab;
    }

    private OnlineDataLab(Context context){
        mContext = context.getApplicationContext();
        mConnection = null;
    }
//=================================================================================================
private HttpURLConnection getConnection(){
    String url_s = getStringUrl();
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

private String getStringUrl(){

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
