package com.wolff.wbase.datalab;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wolff.wbase.tools.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataSender {
    //public static final String CATALOG_USERS = "Catalog_Пользователи";
    //public static final String CATALOG_TASKS = "Catalog_Tasks";

    private static OnlineDataSender sDataLab;

    private Context mContext;

    public static OnlineDataSender get(Context context){
        if(sDataLab==null){
            sDataLab = new OnlineDataSender(context);
        }
        return sDataLab;
    }

    private OnlineDataSender(Context context){
        mContext=context;

    }
//==================================================================================================
    public JSONObject getObjectOnline(String catalog,String guid){
        JSONObject item = null;
        try {
            item = new Get_ObjectOnline_task().execute(catalog,guid).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            //return null;
        }
        return item;
    }
//==================================================================================================
    private class Get_ObjectOnline_task extends AsyncTask<String,Void,JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            return get_ObjectOnline(params[0],params[1]);
        }

    }

    private JSONObject get_ObjectOnline(String catalog,String guid){
        try {
            OnlineDataReceiver receiver = new OnlineDataReceiver();
            String jsonString = receiver.getUrlJsonData(mContext,catalog,guid);
            if(jsonString!=null) {
                return new JSONObject(jsonString);
            }else return null;
        } catch (JSONException e) {
            Log.e("get_WUserList","ERROR-2 "+e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("get_WUserList","ERROR-3 "+e.getLocalizedMessage());
            //e.printStackTrace();
            return null;
        }
    }
    //==================================================================================================
    // POST,PATCH
    //==================================================================================================
public boolean postObjectOnline(String typeConnection,String sObjectType, String guid, String data){
    try {
        return new Post_ObjectOnline_task(mContext).execute(typeConnection,sObjectType,guid,data).get();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
    return false;
}
    //==================================================================================================
    private class Post_ObjectOnline_task extends AsyncTask<String,Void,Boolean> {
        private Context mmContext;
        Post_ObjectOnline_task(Context context){
            mmContext = context;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            return post_ObjectOnline(mmContext,params[0],params[1],params[2],params[3]);
        }

    }

    private boolean post_ObjectOnline(Context  context,String typeConnection,String sObjectType, String guid, String s_data){
        boolean isSuccess=false;
        //Log.e("post_ObjectOnline","BEGIN");
        OnlineConnector connector = new OnlineConnector();
        String s_url;
        if(typeConnection.equalsIgnoreCase(CONNECTION_TYPE_POST)) {
            s_url = connector.getStringUrl(context, typeConnection, sObjectType, null);
        }else if (typeConnection.equalsIgnoreCase(CONNECTION_TYPE_PATCH)){
            s_url = connector.getStringUrl(context, typeConnection, sObjectType, guid);
        }else {
            s_url=null;
        }
        Debug.Log("post_ObjectOnline","URL = "+s_url);
        //HttpURLConnection connection = null;
        try {
            HttpURLConnection connection = connector.getConnection(context, typeConnection,s_url);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length", "" + Integer.toString(s_data.getBytes().length));
            //Log.e("post_ObjectOnline","1");
            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            os.write(s_data.getBytes());
            os.flush();
            connection.connect();
            int code = connection.getResponseCode();
            //Log.e("post_ObjectOnline","code = "+connection.getResponseCode());
            if((code>=200)&&(code<=201)) {
                isSuccess = true;
            }else {
                Debug.Log("post_ObjectOnline","requestCode = "+code);
            }
            connection.disconnect();
        } catch (IOException e) {
            Debug.Log("post_ObjectOnline","Что-то пошло не так "+e.getLocalizedMessage());
        }finally {
        }
        return isSuccess;
    }

}
