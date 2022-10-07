package com.mju.exercise.Preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    private static PreferenceUtil preferenceUtil;
    private SharedPreferences sharedPreferences;
    private final String defaultValue = "";

    private PreferenceUtil(Context context){
        sharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("accessToken", defaultValue).apply();
        sharedPreferences.edit().putString("refreshIdx", defaultValue).apply();
        sharedPreferences.edit().putString("userId", defaultValue).apply();
    }

    public static PreferenceUtil getInstance(Context context){
        if(preferenceUtil == null){
            preferenceUtil = new PreferenceUtil(context);
        }
        return preferenceUtil;
    }

    public void setString(String key, String value){
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key){
        try{
            String str = sharedPreferences.getString(key, defaultValue).toString();
            return str;
        }catch (Exception e){
            return "";
        }
    }

}
