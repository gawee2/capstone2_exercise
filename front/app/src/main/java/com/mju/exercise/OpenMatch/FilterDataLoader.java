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
    public void getDataFavDay(){
        list.stream().filter(openMatchDTO -> {

            LocalDateTime localDateTime = openMatchDTO.getPlayDateTime();

            dataLoadedListener.dataLoaded(openMatchDTO);

            return false;
        });
    }

    //거리에 따른 데이터 뽑기
    public void getDataDisDiff(Status.DistanceDiff distanceDiff){

        Double myLat = Double.valueOf(preferenceUtil.getString("lat"));
        Double myLng = Double.valueOf(preferenceUtil.getString("lng"));
        int standardDistance = 0;
        switch (distanceDiff){
            case M100:
                standardDistance = 100;
                break;
            case M500:
                standardDistance = 500;
                break;
            case M1KM:
                standardDistance = 1000;
                break;
            case M3KM:
                standardDistance = 3000;
                break;
            case M3KMUP:
                standardDistance = 10000;
                break;
        }

        //현재 나의 위경도가 있을때만 동작
        if(myLat !=null && myLng != null){
            for(OpenMatchDTO openMatchDTO: list){
                Double tmpLat = openMatchDTO.getLat();
                Double tmpLng = openMatchDTO.getLng();

                //운동장소가 선택된 것들만 거리 비교함
                if(tmpLat != null && tmpLng != null){
                    int dis = computeDistance(myLat, myLng, tmpLat, tmpLng);
                    if(dis < standardDistance){
                        dataLoadedListener.dataLoaded(openMatchDTO);
                    }
                }
            }
        }
    }

    private int computeDistance(Double myLat, Double myLng, Double mapLat, Double mapLng){

        Double R = 6372.8 * 1000;

        Double dLat = Math.toRadians(mapLat - myLat);
        Double dLng = Math.toRadians(mapLng - myLng);
        Double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLng / 2), 2) * Math.cos(Math.toRadians(myLat)) * Math.cos(Math.toRadians(mapLat));
        Double c = 2 * Math.asin(Math.sqrt(a));

        return (int) (R * c);
    }

    private double convertMtoKM(int distance){
        double result = 0.0;
        result = distance / 1000;

        return result;
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
