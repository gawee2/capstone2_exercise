package com.mju.exercise.HttpRequest;


import android.os.AsyncTask;

public class HttpAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private String mURL;
    private boolean mIsRequest;

    public HttpAsyncTask(String url, boolean isRequest) {
        this.mURL = url;
        this.mIsRequest = isRequest;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //AsyncTask로 백그라운드 작업을 싱해하기 전에 실행되는 부분
        //Dialog 같은 것을 띄워 놓으면 좋다.
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (mIsRequest) {
            //GET 방식
            HttpGet httpGet = new HttpGet();
            httpGet.GETFunction(this.mURL);
        } else {
            //Post 방식
            HttpPost httpPost = new HttpPost();
            String POST_result = httpPost.POSTFunction(this.mURL, "Params");
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
