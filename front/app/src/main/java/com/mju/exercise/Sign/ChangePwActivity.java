package com.mju.exercise.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity {

    private TextView tvUserId;
    private TextInputEditText edtNewPw, edtNewPwCheck;
    private Button btnChangePw;
    private RetrofitUtil retrofitUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mju.exercise.R.layout.activity_change_pw);


        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        retrofitUtil = RetrofitUtil.getInstance();
        
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvUserId.setText("ID: " + userId);

        edtNewPw = (TextInputEditText) findViewById(R.id.edtNewPw);
        edtNewPwCheck = (TextInputEditText) findViewById(R.id.edtNewPwCheck);
        btnChangePw = (Button) findViewById(R.id.btnChangePw);

        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId != null || !userId.equals("")){
                    String pw = edtNewPw.getText().toString();
                    String pw2 = edtNewPwCheck.getText().toString();

                    if(pw.equals(pw2)){
                        retrofitUtil.getRetrofitAPI().changePw(userId, pw).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.isSuccessful()){
                                    if(response.body()){
                                        Toast.makeText(getApplicationContext(), "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {

                    }
                }
            }
        });


    }
}
