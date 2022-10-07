package com.mju.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.RetrofitAPI;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.Sign.SignInActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button btnLoginOrUserInfo;

    private PreferenceUtil preferenceUtil;
    private RetrofitUtil retrofitUtil;

    private static boolean isLogined = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //육성연 : 로그인, db관련 테스트중
        initLoginTest();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("로그인", "onRestart");
        Log.d("로그인", preferenceUtil.getString("accessToken"));
        loginCheck();
    }

    public void loginCheck(){
        Log.d("로그인", preferenceUtil.getString("accessToken"));
        if(preferenceUtil.getString("accessToken").equals("")){
            Log.d("로그인", "엑세스 토큰이 비어있음");
            btnLoginOrUserInfo.setText("로그인");
            isLogined = false;
            return;
        }
        Log.d("로그인", "엑세스 토큰이 들었음");
        btnLoginOrUserInfo.setText("프로필");
        isLogined = true;
        return;
    }

    public void initLoginTest() {
        preferenceUtil = PreferenceUtil.getInstance(getApplicationContext());
        retrofitUtil = RetrofitUtil.getInstance();
        btnLoginOrUserInfo = (Button) findViewById(R.id.btnLoginOrUserInfo);
        btnLoginOrUserInfo.setOnClickListener(setOnClickListener);
        loginCheck();
    }

    /**
     * 클릭 이벤트
     */
    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnLoginOrUserInfo) {

                if (isLogined) {
                    //마이페이지 이동
                    startActivity(new Intent(mContext, UserInfoActivity.class));
                } else {
                    //로그인창으로 이동
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }
            }
        }
    };
}