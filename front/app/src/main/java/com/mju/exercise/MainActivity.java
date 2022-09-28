package com.mju.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mju.exercise.HttpRequest.HttpAsyncTask;

public class MainActivity extends AppCompatActivity {

    private Button btnLoginTest, btnAPITest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //육성연 : 로그인, db관련 테스트중
        initLoginTest();
    }

    public void initLoginTest(){
        btnLoginTest = (Button) findViewById(R.id.btnLoginTest);
        btnLoginTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });

        //api 호출 테스트
        btnAPITest = (Button) findViewById(R.id.btnAPITest);
        btnAPITest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HttpAsyncTask httpAsyncTask = new HttpAsyncTask("https://jsonplaceholder.typicode.com/users", true);    //true 면 GET 방식
//                httpAsyncTask.execute();

                HttpAsyncTask httpAsyncTask = new HttpAsyncTask("https://jsonplaceholder.typicode.com/posts", false);    //flase 면 post 방식
                httpAsyncTask.execute();


            }
        });
    }
}