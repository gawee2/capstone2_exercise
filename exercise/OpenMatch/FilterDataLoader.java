package com.mju.exercise.OpenMatch;

import android.content.Context;
import android.os.Build;
import android.telecom.Call;
import android.util.Log;

import com.google.android.gms.common.api.Response;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.StatusEnum.Status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.Callback;

public class FilterDataLoader {

    private Context mContext;
    private DataLoadedListener dataLoadedListener;
    RetrofitUtil retrofitUtil;
    PreferenceUtil preferenceUtil;

    private int cnt;

    //원본 리스트는 건드리지 않음. 다른 필터 했을때 바로 갖고 있는 데이터로 적용하도록
    public FilterDataLoader(Context context){
        this.mContext = context;
        retrofitUtil = RetrofitUtil.getInstance();
        preferenceUtil = PreferenceUtil.getInstance(context);
    }


    //특정일자 딱 골라서 뽑기
    public void getDataPickDay(ArrayList<OpenMatchDTO> list, LocalDateTime pickDay){
        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
        Log.d("필터특정날짜", "getDataPickDay");
        for(OpenMatchDTO openMatchDTO: list){
            String strLocalDateTime = openMatchDTO.getPlayDateTime();
            if(strLocalDateTime != null){
                LocalDateTime localDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    localDateTime = LocalDateTime.parse(strLocalDateTime);
                    LocalDate tmp1 = pickDay.toLocalDate();
                    LocalDate tmp2 = localDateTime.toLocalDate();
                    Log.d("필터특정날짜", tmp1.toString() + " : " + tmp2.toString());

                    if(tmp1.isEqual(tmp2)){
                        tmpList.add(openMatchDTO);
                    }
                }
            }
        }
        dataLoadedListener.dataLoadComplete(tmpList);
    }

    //가까운 거리순으로 뽑기
    public void getDataDistanceSort(ArrayList<OpenMatchDTO> list){
        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
        Double myLat = Double.valueOf(preferenceUtil.getString("lat"));
        Double myLng = Double.valueOf(preferenceUtil.getString("lng"));

        ArrayList<Double> distacnceList = new ArrayList<>();
        HashMap<Double, OpenMatchDTO> map = new HashMap<>();

        if(myLat !=null && myLng != null) {

            Double unde = 100000000.0;
            //우선 각각의 오픈 매치와 나의 거리를 쭉 계산함
            for (OpenMatchDTO openMatchDTO : list) {
                Double distanceToMe = 0.0;
                Double tmpLat = openMatchDTO.getLat();
                Double tmpLng = openMatchDTO.getLng();

                //운동장소가 선택된 것들만 거리 비교함
                if((tmpLat != null && tmpLng != null)) {
                    distanceToMe = computeDistance(myLat, myLng, tmpLat, tmpLng);
                    distacnceList.add(distanceToMe);
                    map.put(distanceToMe, openMatchDTO);
                }else {
                    //운동장소 미정인 애들은 뒤로 미루려고
                    unde += 1;
                    distacnceList.add(unde);
                    map.put(unde, openMatchDTO);
                }
            }
            //거리를 담고 있는 리스트를 정렬함. 가까운게 앞에 옴
            Collections.sort(distacnceList, new Comparator<Double>() {
                @Override
                public int compare(Double t0, Double t1) {
                    return t0.compareTo(t1);
                }
            });

            //최종정렬
            for(Double dd: distacnceList){
                tmpList.add(map.get(dd));
            }
        }
        dataLoadedListener.dataLoadComplete(tmpList);
    }
    //가까운 날짜순으로 뽑기
    public void getDataDaySort(ArrayList<OpenMatchDTO> list){
        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
        //정렬하고
        Collections.sort(list, new Comparator<OpenMatchDTO>() {
            @Override
            public int compare(OpenMatchDTO openMatchDTO, OpenMatchDTO t1) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try{
                        String s1 = openMatchDTO.getPlayDateTime();
                        String s2 = t1.getPlayDateTime();
                        if(s1 != null && s2 != null){
                            //둘 다 값이 있을때
                            LocalDateTime l1 = LocalDateTime.parse(s1);
                            LocalDateTime l2 = LocalDateTime.parse(s2);

                            return l1.compareTo(l2);
                        }else if(s1 != null && s2 == null){
                            return -1;
                        }else if(s1 == null && s2 != null){
                            return 1;
                        }else{
                            //둘 다 미정일때
                            return 0;
                        }
                    }catch (NullPointerException e){
                        //날짜 미정도 처리 했는데 혹시 몰라서 남겨 놓음
                    }
                }
                return 0;
            }
        });

        //데이터 보내주기
        for(OpenMatchDTO openMatchDTO: list){
            tmpList.add(openMatchDTO);
        }
        dataLoadedListener.dataLoadComplete(tmpList);
    }

    //특정요일만 뽑기
    public void getDataFavDay(ArrayList<OpenMatchDTO> list, Status.FavDayType favDayType){
        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
        String tmp = null;
        switch (favDayType){
            case MON:
                tmp = "월요일";
                break;
            case TUE:
                tmp = "화요일";
                break;
            case WED:
                tmp = "수요일";
                break;
            case THU:
                tmp = "목요일";
                break;
            case FRI:
                tmp = "금요일";
                break;
            case SAT:
                tmp = "토요일";
                break;
            case SUN:
                tmp = "일요일";
                break;
        }
        String finalTmp = tmp;
        for(OpenMatchDTO openMatchDTO: list){
            String strLocalDateTime = openMatchDTO.getPlayDateTime();
            if(strLocalDateTime != null){
                LocalDateTime localDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    localDateTime = LocalDateTime.parse(strLocalDateTime);
                    DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

                    if(convertDayOfWeek(dayOfWeek).equals(finalTmp)){
                        tmpList.add(openMatchDTO);
                    }
                }
            }
        }
        dataLoadedListener.dataLoadComplete(tmpList);
    }

    private String convertDayOfWeek(DayOfWeek dayOfWeek){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA);
        }
        return null;
    }

    //거리에 따른 데이터 뽑기
    public void getDataDisDiff(ArrayList<OpenMatchDTO> list, Status.DistanceDiff distanceDiff){

        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
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
                    Double dis = computeDistance(myLat, myLng, tmpLat, tmpLng);
                    if(standardDistance == 10000){
                        if(dis > 3000 && dis <10000){
                            tmpList.add(openMatchDTO);
                        }
                    }else {
                        if(dis < standardDistance){
                            tmpList.add(openMatchDTO);
                        }
                    }
                }
            }
        }
        dataLoadedListener.dataLoadComplete(tmpList);
    }

    private Double computeDistance(Double myLat, Double myLng, Double mapLat, Double mapLng){

        Double R = 6372.8 * 1000;

        Double dLat = Math.toRadians(mapLat - myLat);
        Double dLng = Math.toRadians(mapLng - myLng);
        Double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLng / 2), 2) * Math.cos(Math.toRadians(myLat)) * Math.cos(Math.toRadians(mapLat));
        Double c = 2 * Math.asin(Math.sqrt(a));

        return (Double) (R * c);
    }

    private double convertMtoKM(int distance){
        double result = 0.0;
        result = distance / 1000;

        return result;
    }

    //참여가능 오픈매치만 뽑기
    public void getDataCanJoin(ArrayList<OpenMatchDTO> list){
        ArrayList<OpenMatchDTO> tmpList = new ArrayList<>();
        cnt = 1;
        for(OpenMatchDTO openMatchDTO: list){
            int totalUser = openMatchDTO.getPersonnel();
            retrofitUtil.getRetrofitAPI().getJoinedUserProfiles(openMatchDTO.getId()).enqueue(new retrofit2.Callback<List<ProfileDTO>>() {
                @Override
                public void onResponse(retrofit2.Call<List<ProfileDTO>> call, retrofit2.Response<List<ProfileDTO>> response) {
                    if(response.isSuccessful()){
                        int nowUser = response.body().size();
                        Log.d("필터순차", "총 유저: " + String.valueOf(totalUser) + " 현재 유저: " + String.valueOf(nowUser));
                        if(totalUser > nowUser){
                            Log.d("필터순차", "참가가능한거 있음");
                            tmpList.add(openMatchDTO);
                        }
                    }
                    if(cnt >= list.size()){
                        dataLoadedListener.dataLoadComplete(tmpList);
                    }

                    cnt += 1;
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
        void dataLoadComplete(ArrayList<OpenMatchDTO> list);
    }
}
