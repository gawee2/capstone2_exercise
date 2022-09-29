package com.mju.exercise.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.R;
import com.mju.exercise.StatusEnum.Status;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText edtId, edtPw;
    private Button btnLogin, btnSignUp, btnForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtId = (EditText) findViewById(R.id.signInId);
        edtPw = (EditText) findViewById(R.id.signInPw);

        btnLogin = (Button) findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strId = edtId.getText().toString();
                String strPw = edtPw.getText().toString();
                String json = null;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("userId", strId);
                    jsonObject.accumulate("userPw", strPw);
                    json = jsonObject.toString();

                    HttpAsyncTask httpAsyncTask = new HttpAsyncTask("http://192.168.0.3:8080/login/signIn", Status.Request.POST, json);
                    httpAsyncTask.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                String strParams = "name=" + name + "&" + "password=" + password;


            }
        });

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgetPwActivity.class);
                startActivity(intent);
            }
        });

    }
}
