package com.mju.exercise.HttpRequest;

import com.mju.exercise.Domain.ApiResponseDTO;
import com.mju.exercise.Domain.JwtDTO;
import com.mju.exercise.Domain.MatchingDTO;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.Domain.SignInDTO;
import com.mju.exercise.Domain.SignUpDTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitAPI {

    //회원가입
    @Headers("Content-Type: application/json")
    @POST("/api/user/signUp")
    Call<Boolean> signUp(@Body SignUpDTO signUpDTO);

    @Headers({"Content-Type: application/json"})
    @POST("/api/user/setMyProfile")
    Call<Boolean> setMyProfile(@Body ProfileDTO profileDTO);

    @Headers({"Content-Type: application/json"})
    @GET("/api/user/getUserProfile/{userId}")
    Call<ProfileDTO> getUserProfile(@Path(value="userId", encoded = true) String userId);

    @Headers({"Content-Type: application/json"})
    @GET("/api/user/getUserIndexByUserId/{userId}")
    Call<Long> getUserIndexByUserId(@Path(value="userId", encoded = true) String userId);

    //이미지 업로드
    @Multipart
    @POST("/api/user/upload/image")
    Call<ApiResponseDTO> uploadImg(@Part MultipartBody.Part imgFile);
    //이미지 다운로드
    @Multipart
    @GET("/api/user/getProfileImg/{imgPath}")
    Call<MultipartBody.Part> getProfileImg(@Path(value="imgPath",encoded = true) String imgPath);


    //로그인
    @Headers("Content-Type: application/json")
    @POST("/api/auth/login")
    Call<ApiResponseDTO> login(@Body SignInDTO signInDTO);


    @GET("/api/auth/tokenCheck")
    Call<Boolean> tokenCheck();


    //오픈매치 가져오기 종목별
    @Headers("Content-Type: application/json")
    @GET("/api/match/openMatchList/{sportType}")
    Call<List<OpenMatchDTO>> loadOpenMatchesSportType(@Path(value = "sportType", encoded = true) String sportType);
    //오픈매치 가져오기 전부
    @Headers("Content-Type: application/json")
    @GET("/api/match/openMatchList")
    Call<List<OpenMatchDTO>> loadOpenMatchesAll();
    //내가 만든 오픈매치만 가져오기
    @Headers("Content-Type: application/json")
    @GET("/api/match/openMatchByMe/{userIdx}")
    Call<List<OpenMatchDTO>> loadOpenMatchesCreatedByMe(@Path(value = "userIdx", encoded = true) Long userIdx);
    //내가 참여중인 오픈매치만 가져오기
    @Headers("Content-Type: application/json")
    @GET("/api/match/joinedOpenMatch/{userIdx}")
    Call<List<OpenMatchDTO>> loadOpenMatchesJoined(@Path(value = "userIdx", encoded = true) Long userIdx);


    //오픈매치 생성
    @Headers("Content-Type: application/json")
    @POST("/api/match/openMatch")
    Call<OpenMatchDTO> openMatch(@Body OpenMatchDTO openMatchDTO);
    //오픈매치 참여
    @Headers("Content-Type: application/json")
    @POST("/api/match/joinMatch")
    Call<Long> joinMatch(@Body MatchingDTO matchingDTO);


    //현재 오픈매치에 참여중인 모든 유저 프로필 정보 가져오기
    @Headers("Content-Type: application/json")
    @GET("/api/match/joinedUserProfiles/{openMatchIdx}")
    Call<List<ProfileDTO>> getJoinedUserProfiles(@Path(value = "openMatchIdx", encoded = true) Long openMatchIdx);

}
