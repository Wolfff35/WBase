package com.wolff.wbase.datalab;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wolff.wbase.tools.PreferencesTools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataLab {
private static final int READ_TIMEOUT = 10000;
private static final int CONNECT_TIMEOUT = 15000;
//=================================================================================================

    private int sendDataToServer(Context context,String typeConnection,String catalog,String guid,String data){
        int responseCode=0;
        HttpURLConnection conn = getConnection(context,typeConnection,catalog,guid);
        conn.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            conn.connect();
            responseCode = conn.getResponseCode();
            conn.disconnect();
            os.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }finally {
            //conn.disconnect();
        }
    return responseCode;
    }

    private HttpURLConnection getConnection(Context context, String typeConnection,String catalog,String guid){
        //for GET PATCH POST
        String url_s = getStringUrl(context,typeConnection,catalog,guid);
        try {
            URL url = new URL(url_s);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String authString = getAuthorization1CBase(context);
            connection.setRequestProperty("Authorization","Basic "+authString);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestMethod(typeConnection);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            return connection;
        } catch (IOException e) {
            Log.e("get connection","NO CONNECTION "+e.getLocalizedMessage());
            return null;
        }
    }

    private String getFiltersForQuery(Context context, String catalog){
        return "";
    }
    private String getStringUrl(Context context,String typeConnection, String catalog,String guid){
        switch (typeConnection){
            case "GET": {
            return Uri.parse(getBaseUrl(context) + catalog+"(guid'"+guid+"')" +"/")
                    .buildUpon()
                    .appendQueryParameter("$format", "json")
                    .build()
                    .toString()
                    +getFiltersForQuery(context,catalog);
            //return getBaseUrl(context) + catalog + "/?$format=json"+getFiltersForQuery(context,catalog);
            }
            case "PATCH":{
               return Uri.parse(getBaseUrl(context) + catalog+"(guid'"+guid+"')"  + "/")
                       .buildUpon().appendQueryParameter("$format", "json")
                       .build()
                       .toString();
            }
            case "POST":{
               return Uri.parse(getBaseUrl(context) + catalog)
                       .buildUpon()
                       .build()
                       .toString();
            }
        }
        return null;
    }
    private String getAuthorization1CBase(Context context){
        PreferencesTools pref = new PreferencesTools();
        return pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_LOGIN)
                +":"
                +pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_PASSWORD);
    }
    private String getBaseUrl(Context context){
        PreferencesTools pref = new PreferencesTools();
        return "http://"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_SERVER_NAME)
                +"/"+pref.getStringPreference(context,PreferencesTools.PREFERENCE_BASE_NAME)
                +"/odata/standard.odata/";

    }
    //=====================================================================================
    public byte[] getUrlBytes(Context context,String catalog) throws IOException {
        HttpURLConnection connection = getConnection(context,"GET",catalog,null);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with ");
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(Context context,String catalog) throws IOException {
        return new String(getUrlBytes(context,catalog));
    }

    ////============================================================================================
    public class SendDataToServer_task extends AsyncTask<String,Void,Integer>{
        private Context mContext;
        public SendDataToServer_task(Context context){
            mContext=context;
        }
        @Override
        protected Integer doInBackground(String... params) {

            return sendDataToServer(mContext,params[0],params[1],params[2],params[3]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer==200|integer==201){
                Log.e("SendDataToServer_task","SUSSESS");
            }else {
                Log.e("SendDataToServer_task","ERROR code "+integer);
            }
        }
    }
}
