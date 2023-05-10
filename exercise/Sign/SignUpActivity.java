package com.mju.exercise.Sign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mju.exercise.Domain.SignUpDTO;
import com.mju.exercise.HttpRequest.RetrofitAPI;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText edtId, edtPw, edtCheckPw, edtEmail;
    Button btnSignUp;
    private RetrofitUtil retrofitUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

    }

    private void init(){
        edtId = (TextInputEditText) findViewById(R.id.signUpId);
        edtPw = (TextInputEditText) findViewById(R.id.signUpPw);
        edtCheckPw = (TextInputEditText) findViewById(R.id.signUpCheckPw);
        edtEmail = (TextInputEditText) findViewById(R.id.signUpEmail);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        retrofitUtil = RetrofitUtil.getInstance();

        btnSignUp.setOnClickListener(setOnClickListener);
    }

    //클릭 이벤트
    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //회원가입
            if(view == btnSignUp){

                //가입 필수 조건에 대해서는 입력 확인 필요할듯
                String strId = edtId.getText().toString();
                String strPw = edtPw.getText().toString();
                String strCheckPw = edtCheckPw.getText().toString();
                String strEmail = edtEmail.getText().toString();

                //비밀번호 2칸 모두 동일하게 적으면 통과
                if(strPw.equals(strCheckPw)){

                    Log.d("http리턴","버튼 눌림");

                    SignUpDTO param = new SignUpDTO();
                    param.setUserId(strId);
                    param.setUserPw(strPw);
                    param.setEmail(strEmail);

                    retrofitUtil.getRetrofitAPI().signUp(param).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful()){
                                Log.d("http리턴","응답 성공");
                                boolean success = response.body();
                                if(success){
                                    Log.d("http", "회원가입 성공!!!!");

                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디", Toast.LENGTH_SHORT).show();
                                    Log.d("http", "회원가입 실패");
                                }
                            }else {
                                Log.d("http리턴","응답 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("http", "onFailure 회원가입 실패");
                        }
                    });

                }else{
                    return;
                }

            }

        }
    };

}
