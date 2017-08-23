package com.wolff.wbase.datalab;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wolff.wbase.model.WUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataLab {
    public static final String CATALOG_USERS = "Catalog_Пользователи";
    public static final String CATALOG_TEST_DOC = "Catalog_Пользователи";
    //=================================================================================================
    public ArrayList<WUser> get_WUserList(Context context){
       //TODO переделать на метод обьекта
        ArrayList<WUser> users = new ArrayList<>();
        OnlineDataReceiver receiver = new OnlineDataReceiver();
        try {
            String jsonString = receiver.getUrlString(context,CATALOG_USERS,null);
            JsonParcer parcer = new JsonParcer();
            JSONObject jsonObject = new JSONObject(jsonString);
            users = parcer.parse_WUserList(jsonObject);
          } catch (JSONException e) {
              Log.e("get_WUserList","ERROR2 "+e.getLocalizedMessage());
          }
            return users;
   }


/*    private int sendDataToServer(Context context,String typeConnection,String catalog,String guid,String data){
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

    //=====================================================================================
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
*/
}
