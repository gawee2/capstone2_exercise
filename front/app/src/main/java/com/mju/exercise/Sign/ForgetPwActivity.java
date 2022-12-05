package com.mju.exercise.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.R;
import com.mju.exercise.StatusEnum.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ForgetPwActivity extends AppCompatActivity {

    Button btnSendAuthCode, btnCheckAuthCode;
    TextInputEditText edtId, edtAuthCode;
    String strAuthCode;
    RetrofitUtil retrofitUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpw);

        init();
    }

    private void init(){
        btnCheckAuthCode = (Button) findViewById(R.id.btnCheckAuthCode);
        btnSendAuthCode = (Button) findViewById(R.id.btnSendAuthCode);
        edtAuthCode = (TextInputEditText) findViewById(R.id.edtAuthCode);
        edtId = (TextInputEditText) findViewById(R.id.edtId);

        btnSendAuthCode.setOnClickListener(onClickListener);
        btnCheckAuthCode.setOnClickListener(onClickListener);

        retrofitUtil = RetrofitUtil.getInstance();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == btnSendAuthCode){
                strAuthCode = randomAuthCode();
                String json = null;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("message", strAuthCode);
                    jsonObject.accumulate("userId", edtId.getText().toString());
                    json = jsonObject.toString();

                    HttpAsyncTask httpAsyncTask = new HttpAsyncTask(retrofitUtil.getBASE_URL() + "email/send", Status.Request.POST, json);
                    httpAsyncTask.execute();

                    Toast.makeText(getApplicationContext(), "인증코드 발송 요청 완료", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if(view == btnCheckAuthCode){
                if(edtAuthCode.getText().toString().equals(strAuthCode)) {
                    Log.d("인증코드", "일치");
                    Intent intent = new Intent(getApplicationContext(), ChangePwActivity.class);
                    intent.putExtra("userId", edtId.getText().toString());
                    startActivity(intent);
                    finish();
                }else{
                    Log.d("인증코드","불일치");
                    Toast.makeText(getApplicationContext(), "인증코드 불일치", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };



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
