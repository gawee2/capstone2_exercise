package com.mju.exercise.Sign;

import android.os.Bundle;
import android.os.PersistableBundle;
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

import java.util.Random;

public class ForgetPwActivity extends AppCompatActivity {

    Button btnSendAuthCode, btnCheckAuthCode;
    EditText edtId, edtAuthCode;
    String strAuthCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpw);

        btnCheckAuthCode = (Button) findViewById(R.id.btnCheckAuthCode);
        btnSendAuthCode = (Button) findViewById(R.id.btnSendAuthCode);
        edtAuthCode = (EditText) findViewById(R.id.edtAuthCode);
        edtId = (EditText) findViewById(R.id.edtId);

        //인증코드 보내기 버튼
        //서버로 이메일 발송 요청
        btnSendAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strAuthCode = randomAuthCode();
                String json = null;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("message", strAuthCode);
                    jsonObject.accumulate("userId", edtId.getText().toString());
                    json = jsonObject.toString();

                    HttpAsyncTask httpAsyncTask = new HttpAsyncTask("http://192.168.0.3:8080/email/send", Status.Request.POST, json);
                    httpAsyncTask.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        btnCheckAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAuthCode.getText().toString().equals(strAuthCode)) {
                    Log.d("인증코드", "일치");
                }else{
                    Log.d("인증코드","불일치");
                }
            }
        });
    }

    public String randomAuthCode(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        Log.d("인증코드", generatedString);
        return generatedString;
    }

}
