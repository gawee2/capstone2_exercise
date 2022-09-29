package com.mju.exercise.Sign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.R;
import com.mju.exercise.StatusEnum.Status;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {

    EditText edtId, edtPw, edtCheckPw, edtEmail;
    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.signUpId);
        edtPw = (EditText) findViewById(R.id.signUpPw);
        edtCheckPw = (EditText) findViewById(R.id.signUpCheckPw);
        edtEmail = (EditText) findViewById(R.id.signUpEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //가입 필수 조건에 대해서는 입력 확인 필요할듯
                String strId = edtId.getText().toString();
                String strPw = edtPw.getText().toString();
                String strCheckPw = edtCheckPw.getText().toString();
                String strEmail = edtEmail.getText().toString();

                //비밀번호 2칸 모두 동일하게 적으면 통과
                if(strPw.equals(strCheckPw)){

                    String json = "";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.accumulate("userId", strId);
                        jsonObject.accumulate("userPw", strPw);
                        jsonObject.accumulate("email", strEmail);

                        json = jsonObject.toString();
                        Log.d("JSON", json);

                        HttpAsyncTask httpAsyncTask = new HttpAsyncTask("http://192.168.0.3:8080/login/signUp", Status.Request.POST, json);
                        httpAsyncTask.execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    return;
                }
            }
        });


    }
}
