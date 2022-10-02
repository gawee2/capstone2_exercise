package com.mju.exercise.HttpRequest;

import com.mju.exercise.Domain.ApiResponseDTO;
import com.mju.exercise.Domain.JwtDTO;
import com.mju.exercise.Domain.SignInDTO;
import com.mju.exercise.Domain.SignUpDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    final String BASE_URL = "http://192.168.0.3:8080";

    //회원가입
    @Headers("Content-Type: application/json")
    @POST("/api/user/signUp")
    Call<Boolean> signUp(@Body SignUpDTO signUpDTO);

    //로그인
    @Headers("Content-Type: application/json")
    @POST("/api/auth/login")
    Call<ApiResponseDTO> login(@Body SignInDTO signInDTO);

    //토큰 가지고 api 테스트
    @GET("/api/user/info/test")
    Call<String> test();

    @GET("/api/auth/tokenCheck")
    Call<Boolean> tokenCheck();
}
