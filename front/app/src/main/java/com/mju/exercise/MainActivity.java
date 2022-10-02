package com.mju.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.RetrofitAPI;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.Sign.SignInActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button btnLoginTest, btnAPITest, btnUserInfo;
    public static PreferenceUtil preferenceUtil;

    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //육성연 : 로그인, db관련 테스트중
        initLoginTest();

    }

    public void initLoginTest() {
        preferenceUtil = PreferenceUtil.getInstance(getApplicationContext());

        btnLoginTest = (Button) findViewById(R.id.btnLoginTest);
        btnLoginTest.setOnClickListener(setOnClickListener);

        //api 호출 테스트
        btnAPITest = (Button) findViewById(R.id.btnAPITest);
        btnAPITest.setOnClickListener(setOnClickListener);

        //마이페이지
        btnUserInfo = (Button) findViewById(R.id.btnUserInfo);
        btnUserInfo.setOnClickListener(setOnClickListener);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);

    }

    /**
     * 클릭 이벤트
     */
    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnLoginTest) {
                //로그인
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            } else if (v == btnAPITest) {

                //로그인하고 토큰으로 api요청 되는지 체크중
                String str = preferenceUtil.getString("accessToken");
                Log.d("로그인", "토큰 디바이스에 저장됨: " + str);

            } else if (v == btnUserInfo) {
                //마이페이지 이동
                startActivity(new Intent(mContext, UserInfoActivity.class));
            }
        }
    };
}