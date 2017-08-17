package com.wolff.wbase.datalab;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by wolff on 17.08.2017.
 */

public class OnlineDataReceiver {

    public String getUrlString(Context context,String catalog,String guid) {
        return new String(getUrlBytes(context,catalog,guid));
    }

    private byte[] getUrlBytes(Context context, String catalog,String guid) {
        OnlineConnector connector = new OnlineConnector();
        String url_s = connector.getStringUrl(context,OnlineConnector.CONNECTION_TYPE_GET,catalog,guid);
        HttpURLConnection connection = connector.getConnection(context,OnlineConnector.CONNECTION_TYPE_GET,url_s);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            Log.e("RESPONCE","Code = "+connection.getResponseCode());
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
            Log.e("getURLBytes","ERROR");
            return out.toByteArray();
        }catch (IOException e) {
            Log.e("getURLBytes","ERROR "+e.getLocalizedMessage());
            return null;
        }
        finally {
        connection.disconnect();
        }
    }

}
