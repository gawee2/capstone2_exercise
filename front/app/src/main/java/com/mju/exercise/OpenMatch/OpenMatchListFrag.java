package com.mju.exercise.OpenMatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.R;
import com.mju.exercise.StatusEnum.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpenMatchListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenMatchListFrag extends Fragment implements OpenMatchFilter{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SPORT = "sport";

    // TODO: Rename and change types of parameters
    private int mSportType;

    ArrayList<OpenMatchDTO> openMatches;
    ListView customListView;
    RetrofitUtil retrofitUtil;
    OpenMatchAdapter openMatchAdapter;

    public OpenMatchListFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OpenMatchListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenMatchListFrag newInstance(int param1) {
        OpenMatchListFrag fragment = new OpenMatchListFrag();
        Bundle args = new Bundle();
        args.putInt(SPORT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSportType = getArguments().getInt(SPORT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrofitUtil = RetrofitUtil.getInstance();
        openMatches = new ArrayList<>();
        loadOpenMatchesSportType(sportTypeToString(mSportType));

        View view = inflater.inflate(R.layout.fragment_open_match_list, container, false);

        customListView = (ListView) view.findViewById(R.id.listViewOpenMatchList);

        // Inflate the layout for this fragment
        return view;
    }


    public void loadOpenMatchesSportType(String sport){

        retrofitUtil.getRetrofitAPI().loadOpenMatchesSportType(sport).enqueue(new Callback<List<OpenMatchDTO>>() {
            @Override
            public void onResponse(Call<List<OpenMatchDTO>> call, Response<List<OpenMatchDTO>> response) {
                if(response.isSuccessful()){
                    openMatches = (ArrayList<OpenMatchDTO>) response.body();
                    openMatchAdapter = new OpenMatchAdapter(getContext(), openMatches);
                    customListView.setAdapter(openMatchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OpenMatchDTO>> call, Throwable t) {

            }
        });
    }

    private String sportTypeToString(int mSportType){
        switch (mSportType){
            case R.id.btnSoccer:
                return "축구";
            case R.id.btnFutsal:
                return "풋살";
            case R.id.btnBaseball:
                return "야구";
            case R.id.btnBasketball:
                return "농구";
            case R.id.btnBadminton:
                return "배드민턴";
            case R.id.btnCycle:
                return "사이클";
        }

        return "없음";
    }


    @Override
    public void setFilter(Status.FilterTypeJoin filterTypeJoin, Status.FilterTypeDistance filterTypeDistance,
                          Status.FilterTypeDay filterTypeDay, Status.DistanceDiff distanceDiff,
                          Status.FavDayType favDayType,
                          LocalDateTime localDateTime) {

        if(filterTypeJoin == Status.FilterTypeJoin.JOIN_DEFAULT && filterTypeDay == Status.FilterTypeDay.DAY_DEFAULT && filterTypeDistance == Status.FilterTypeDistance.DISTANCE_DEFAULT){
            //모두 디폴트면 기존 리스트 그대로 보여줌
            openMatchAdapter = new OpenMatchAdapter(getContext(), openMatches);
            customListView.setAdapter(openMatchAdapter);
        }else {
            //무언가 필터링 할 게 있으면 새로 리스트 만들고 어댑터에 연결
            ArrayList<OpenMatchDTO> newOpenMatches = new ArrayList<>();
            openMatchAdapter = new OpenMatchAdapter(getContext(), newOpenMatches);
            customListView.setAdapter(openMatchAdapter);

            FilterDataLoader filterDataLoader = new FilterDataLoader(getContext(), openMatches);
            filterDataLoader.setDataListener(new FilterDataLoader.DataLoadedListener() {
                @Override
                public void dataLoaded(OpenMatchDTO openMatchDTO) {
                    newOpenMatches.add(openMatchDTO);
                    openMatchAdapter.notifyDataSetChanged();
                }

                @Override
                public void dataLoadComplete() {

                }
            });

            //참가 가능 여부로 필터링
            if(filterTypeJoin == Status.FilterTypeJoin.JOIN_CAN){
                canJoin(filterDataLoader);
            }

            //거리로 필터링
            if(filterTypeDistance == Status.FilterTypeDistance.DISTANCE_DIFFERENCE){
                distanceInner(filterDataLoader, distanceDiff);
            }else if(filterTypeDistance == Status.FilterTypeDistance.DISTANCE_NEAR){
                distanceSort(filterDataLoader);
            }
            //날짜로 필터링
            if(filterTypeDay == Status.FilterTypeDay.DAY_FAVDAY){
                dayFav(filterDataLoader, favDayType);
            }else if(filterTypeDay == Status.FilterTypeDay.DAY_NEAR){
                daySort(filterDataLoader);
            }else if(filterTypeDay == Status.FilterTypeDay.DAY_PICK){
                dayPick(filterDataLoader, localDateTime);
            }
        }

    }



    public void dayFav(FilterDataLoader filterDataLoader, Status.FavDayType favDayType){
        filterDataLoader.getDataFavDay(favDayType);
    }
    public void dayPick(FilterDataLoader filterDataLoader, LocalDateTime localDateTime){
        filterDataLoader.getDataPickDay(localDateTime);
    }
    public void daySort(FilterDataLoader filterDataLoader){
        filterDataLoader.getDataDaySort();
    }


    public void distanceInner(FilterDataLoader filterDataLoader, Status.DistanceDiff diff){
        filterDataLoader.getDataDisDiff(diff);
    }

    public void distanceSort(FilterDataLoader filterDataLoader){
        filterDataLoader.getDataDistanceSort();
    }

    public void canJoin(FilterDataLoader filterDataLoader){
        filterDataLoader.getDataCanJoin();
    }
}