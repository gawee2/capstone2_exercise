package com.mju.exercise;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.StatusEnum.Status;

public class LoginActivity extends AppCompatActivity {

    private EditText edtId, edtPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPw = (EditText) findViewById(R.id.edtPw);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtId.getText().toString();
                String password = edtPw.getText().toString();

                String strParams = "name=" + name + "&" + "password=" + password;
                HttpAsyncTask httpAsyncTask = new HttpAsyncTask("http://192.168.0.3:8080/login/signUp", Status.Request.POST, strParams);
                httpAsyncTask.execute();

                Log.d("로그인", name + " " + password);
            }
        });

    }
}
