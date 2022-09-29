package com.mju.exercise.HttpRequest;


import android.os.AsyncTask;

public class HttpAsyncTask extends AsyncTask<Void, Void, String> {
    private String mURL, params;
    private com.mju.exercise.StatusEnum.Status.Request requestType;


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
    protected String doInBackground(Void... voids) {
        String htttpResult;
        if (requestType == com.mju.exercise.StatusEnum.Status.Request.GET) {
            //GET 방식
            HttpGet httpGet = new HttpGet();
            htttpResult = httpGet.GETFunction(this.mURL);
        } else {
            //Post 방식
            HttpPost httpPost = new HttpPost();
            htttpResult = httpPost.POSTFunction(this.mURL, this.params);
        }
        return htttpResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
