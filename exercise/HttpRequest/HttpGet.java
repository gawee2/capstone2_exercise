package com.mju.exercise.HttpRequest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGet {
    String EXCEPTION_ERROR = "Exception Occured. Check the url";

    public String GETFunction(String mUrl) {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn != null){
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                conn.setConnectTimeout(15000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_CREATED || resCode == HttpURLConnection.HTTP_OK){
                    InputStream is = conn.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String result = null;

                    while ((result = br.readLine()) != null) {
                        sb.append(result + '\n');
                    }

                    conn.disconnect();
                    br.close();
                    result = sb.toString();
                    Log.d("HttpGet_Result", result);

                    return result;

                }else {
                    Log.d("HttpGet_Result", String.valueOf(resCode));
                }

            }else{
                Log.d("HttpGet_Result", "conn null");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("HttpGet_Error", EXCEPTION_ERROR);
        return null;
    }
}
