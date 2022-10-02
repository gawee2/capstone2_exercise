package com.mju.exercise.Sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mju.exercise.Domain.ApiResponseDTO;
import com.mju.exercise.Domain.SignInDTO;
import com.mju.exercise.HttpRequest.HttpAsyncTask;
import com.mju.exercise.HttpRequest.RetrofitAPI;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.R;
import com.mju.exercise.StatusEnum.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.PhantomReference;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private EditText edtId, edtPw;
    private Button btnLogin, btnSignUp, btnForgetPassword;

    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;

    PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();
    }

    private void init(){
        preferenceUtil = PreferenceUtil.getInstance(getApplicationContext());

        edtId = (EditText) findViewById(R.id.signInId);
        edtPw = (EditText) findViewById(R.id.signInPw);

        btnLogin = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);

        btnLogin.setOnClickListener(onClickListener);
        btnSignUp.setOnClickListener(onClickListener);
        btnForgetPassword.setOnClickListener(onClickListener);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == btnLogin){

                SignInDTO signInDTO = new SignInDTO();
                signInDTO.setUserId(edtId.getText().toString());
                signInDTO.setUserPw(edtPw.getText().toString());

                retrofitAPI.login(signInDTO).enqueue(new Callback<ApiResponseDTO>() {
                    @Override
                    public void onResponse(Call<ApiResponseDTO> call, Response<ApiResponseDTO> response) {
                        Log.d("로그인", "응답");
                        if(response.isSuccessful()){
                            Log.d("로그인", "성공");
                            ApiResponseDTO apiResponseDTO = response.body();
                            Log.d("로그인", String.valueOf(apiResponseDTO.getCode()));
                            try{
                                preferenceUtil.setString("refreshIdx", String.valueOf(apiResponseDTO.getResult().getRefreshIdx()));
                                preferenceUtil.setString("accessToken", apiResponseDTO.getResult().getAccessToken());
                                Log.d("로그인", String.valueOf(apiResponseDTO.getResult().getRefreshIdx()));
                                Log.d("로그인", String.valueOf(apiResponseDTO.getResult().getAccessToken()));
                            }catch (NullPointerException e){
                                Log.d("로그인", "이미 토큰이 발급된 사용자");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseDTO> call, Throwable t) {

                    }
                });


            }else if(view == btnSignUp){
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }else if(view == btnForgetPassword){
                Intent intent = new Intent(getApplicationContext(), ForgetPwActivity.class);
                startActivity(intent);
            }
        }


    };
}
