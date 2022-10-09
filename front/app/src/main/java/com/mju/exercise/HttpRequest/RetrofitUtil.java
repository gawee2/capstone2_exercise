package com.mju.exercise.HttpRequest;

import android.content.Context;

import com.mju.exercise.Preference.PreferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil;
    private OkHttpClient.Builder httpClient;
    private OkHttpClient client;

    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;

    private static String token = "";
//    final String BASE_URL = "http://192.168.0.3:8080/";
    final String BASE_URL = "http://3.34.253.207:8080/";


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RetrofitAPI getRetrofitAPI() {
        return retrofitAPI;
    }

    public static void setToken(String token) {
        RetrofitUtil.token = token;
    }

    private RetrofitUtil(){
        this.httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("token", token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        this.client = httpClient.build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(this.client)
                .build();

        this.retrofitAPI = this.retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitUtil getInstance(){
        if(retrofitUtil == null){
            retrofitUtil = new RetrofitUtil();
        }
        return retrofitUtil;
    }

    public String getBASE_URL(){
        return this.BASE_URL;
    }

    //마지막 슬래시 지우고 리턴
    public String getBASE_URL_NONE_SLASH(){
        String str = this.BASE_URL;
        return str.substring(0, str.length() - 1);
    }


}
