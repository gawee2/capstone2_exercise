package com.mju.exercise.HttpRequest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpPost {
    private final String EXCEPTION_ERROR = "Exception Occured. Check the url";

    public String POSTFunction(String mUrl, String params) {

        try {
            //받아온 String을 url로 만들어주기
            URL url = new URL(mUrl);

            //conn으로 url connection을 open 해주기
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //불러오는데 시간이 오래 걸리는 경우 Time out 설정
            httpURLConnection.setReadTimeout(10000);
            //연결하는데 시간이 오래 걸리는 경우 Time out 설정
            httpURLConnection.setConnectTimeout(15000);
            //연결 방법 설정
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            //Accept-Charset 설정 UTF-8 or ASCII
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            // POST로 넘겨줄 파라미터 생성.
            byte[] outputInBytes = params.getBytes(StandardCharsets.UTF_8);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(outputInBytes);
            os.close();

            //결과값을 받아온다.
            InputStream is = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            String res = response.toString();
            res = res.trim();
            Log.d("HttpPOST_Result", res);
            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("ERROR", EXCEPTION_ERROR);
        return null;
    }
}
