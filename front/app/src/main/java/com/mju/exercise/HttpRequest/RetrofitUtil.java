package com.mju.exercise.HttpRequest;

import android.content.Context;

import com.mju.exercise.Preference.PreferenceUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil;
    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RetrofitAPI getRetrofitAPI() {
        return retrofitAPI;
    }

    private RetrofitUtil(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.retrofitAPI = this.retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitUtil getInstance(){
        if(retrofitUtil == null){
            retrofitUtil = new RetrofitUtil();
        }
        return retrofitUtil;
    }


}
