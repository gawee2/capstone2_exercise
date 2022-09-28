package com.mju.exercise.HttpRequest;


import android.os.AsyncTask;
import com.mju.exercise.StatusEnum.Status;

public class HttpAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private String mURL;
//    private boolean mIsRequest;
    private com.mju.exercise.StatusEnum.Status.Request requestType;
    private String params;


    //생성자 오버로딩 GET
    public HttpAsyncTask(String url, com.mju.exercise.StatusEnum.Status.Request requestType) {
        this.mURL = url;
        this.requestType = requestType;
    }
    //POST
    public HttpAsyncTask(String url, com.mju.exercise.StatusEnum.Status.Request requestType, String params) {
        this.mURL = url;
        this.requestType = requestType;
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //AsyncTask로 백그라운드 작업을 싱해하기 전에 실행되는 부분
        //Dialog 같은 것을 띄워 놓으면 좋다.
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (requestType == com.mju.exercise.StatusEnum.Status.Request.GET) {
            //GET 방식
            HttpGet httpGet = new HttpGet();
            httpGet.GETFunction(this.mURL);
        } else {
            //Post 방식
            HttpPost httpPost = new HttpPost();
            String POST_result = httpPost.POSTFunction(this.mURL, this.params);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
