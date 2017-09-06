package com.wolff.wbase.datalab;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.tools.Debug;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by wolff on 17.08.2017.
 */

public class OnlineDataReceiver {

    public String getUrlJsonData(Context context, String catalog, String guid) throws IOException {
        byte[] b = getUrlBytes(context,catalog,guid);
        if(b!=null) {
            return new String(b);
        }else return null;
    }

    private byte[] getUrlBytes(Context context, String catalog,String guid) throws IOException {
        OnlineConnector connector = new OnlineConnector();
        String url_s = connector.getStringUrl(context,OnlineConnector.CONNECTION_TYPE_GET,catalog,guid);
        HttpURLConnection connection = connector.getConnection(context,OnlineConnector.CONNECTION_TYPE_GET,url_s);
        if(connection==null){
            Debug.Log("getUrlBytes","Нет соединения");
            return null;
        }
        int resCode = connection.getResponseCode();
        if(resCode<200&&resCode>201){
            Debug.Log("RESPONCE","Code = "+connection.getResponseCode()+"; Catalog =  "+catalog+"; guid = "+guid);
            return null;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }catch (IOException e) {
            Debug.Log("getURLBytes","ERROR "+e.getLocalizedMessage());
            return null;
        }finally {
            connection.disconnect();
        }
    }

}
