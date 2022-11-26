package com.mju.exercise.OpenMatch;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import com.google.android.gms.common.api.Response;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.StatusEnum.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class FilterDataLoader {

    private Context mContext;
    private ArrayList<OpenMatchDTO> list;
    private DataLoadedListener dataLoadedListener;
    RetrofitUtil retrofitUtil;
    PreferenceUtil preferenceUtil;

    public FilterDataLoader(Context context, ArrayList<OpenMatchDTO> list){
        this.list = (ArrayList<OpenMatchDTO>) list.clone();
        this.mContext = context;
        retrofitUtil = RetrofitUtil.getInstance();
        preferenceUtil = PreferenceUtil.getInstance(context);
    }

    //특정요일만 뽑기
    public void getFavDay(){
        list.stream().filter(openMatchDTO -> {

            LocalDateTime localDateTime = openMatchDTO.getPlayDateTime();

            dataLoadedListener.dataLoaded(openMatchDTO);

            return false;
        });
    }

    //참여가능 오픈매치만 뽑기
    public void getDataCanJoin(){
            for(OpenMatchDTO openMatchDTO: list){
                int totalUser = openMatchDTO.getPersonnel();
                retrofitUtil.getRetrofitAPI().getJoinedUserProfiles(openMatchDTO.getId()).enqueue(new retrofit2.Callback<List<ProfileDTO>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<ProfileDTO>> call, retrofit2.Response<List<ProfileDTO>> response) {
                        if(response.isSuccessful()){
                            int nowUser = response.body().size();
                            Log.d("필터", "총 유저: " + String.valueOf(totalUser) + " 현재 유저: " + String.valueOf(nowUser));
                            if(totalUser > nowUser){
                                Log.d("필터", "참가가능한거 있음");
                                dataLoadedListener.dataLoaded(openMatchDTO);
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<ProfileDTO>> call, Throwable t) {

                    }
                });
            }
        }



    public void setDataListener(DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
    }

    public interface DataLoadedListener {
        void dataLoaded(OpenMatchDTO openMatchDTO);
    }
}
