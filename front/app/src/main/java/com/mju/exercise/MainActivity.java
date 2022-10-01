package com.mju.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.Sign.SignInActivity;
import com.mju.exercise.StatusEnum.Status;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button btnLoginTest, btnAPITest, btnUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //육성연 : 로그인, db관련 테스트중
        initLoginTest();
    }

    public void initLoginTest() {
        btnLoginTest = (Button) findViewById(R.id.btnLoginTest);
        btnLoginTest.setOnClickListener(setOnClickListener);

        //api 호출 테스트
        btnAPITest = (Button) findViewById(R.id.btnAPITest);
        btnAPITest.setOnClickListener(setOnClickListener);

        //마이페이지
        btnUserInfo = (Button) findViewById(R.id.btnUserInfo);
        btnUserInfo.setOnClickListener(setOnClickListener);
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
                //API 호출 테스트
                //HttpAsyncTask httpAsyncTask = new HttpAsyncTask("https://jsonplaceholder.typicode.com/users", Status.Request.GET);    //true 면 GET 방식
                //httpAsyncTask.execute();

                HttpAsyncTask httpAsyncTask = new HttpAsyncTask("http://192.168.0.3:8080/login/userList", Status.Request.GET);    //true 면 GET 방식
                httpAsyncTask.execute();

                //HttpAsyncTask httpAsyncTask = new HttpAsyncTask("https://jsonplaceholder.typicode.com/posts", false);    //flase 면 post 방식
                //httpAsyncTask.execute();
            } else if (v == btnUserInfo) {
                //마이페이지 이동
                startActivity(new Intent(mContext, UserInfoActivity.class));
            }
        }
    };
}