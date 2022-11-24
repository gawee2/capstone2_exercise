package com.mju.exercise.OpenMatch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mju.exercise.Domain.MatchingDTO;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.PopupMapActivity;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.Profile.SmallProfileAdapter;
import com.mju.exercise.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenMatchAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List list;
    RetrofitUtil retrofitUtil;
    PreferenceUtil preferenceUtil;

    public OpenMatchAdapter(@NonNull Context context, @NonNull ArrayList list) {
        super(context, 0, list);
        this.mContext = context;
        this.list = list;

        retrofitUtil = RetrofitUtil.getInstance();
        preferenceUtil = PreferenceUtil.getInstance(context);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class ViewHolder{
        public TextView tvSubect;
        public TextView tvArticle;
        public TextView tvSportType;
        public TextView tvPersonnel;
        public TextView tvPlayDateTime;
        public TextView tvDistanceToMe;

        public Button btnDetailOnMap, btnDetailJoin;

        public Double myLat, myLng;
        public Double mapLat, mapLng;
        public int distanceToMe;

        //스몰 프로필 부분
        public ArrayList<ProfileDTO> profileDTOs;
        public ListView customListView;
        public SmallProfileAdapter smallProfileAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.open_match_item, parent, false);
        }

        ExpandableLayout expandableLayout = (ExpandableLayout) convertView.findViewById(R.id.exItem);
        expandableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableLayout.isExpanded()){
                    expandableLayout.collapse();
                }else {
                    expandableLayout.expand();
                }
            }
        });

        viewHolder = new ViewHolder();

        //프리뷰 부분
        viewHolder.tvSubect = (TextView) convertView.findViewById(R.id.omSubject);
        viewHolder.tvSportType = (TextView) convertView.findViewById(R.id.omSportType);
        viewHolder.tvPersonnel = (TextView) convertView.findViewById(R.id.omPersonnel);
        viewHolder.tvPlayDateTime = (TextView) convertView.findViewById(R.id.omPlayDateTime);
        viewHolder.tvDistanceToMe = (TextView) convertView.findViewById(R.id.omDistanceToMe);


        //디테일 부분
        viewHolder.tvArticle = (TextView) expandableLayout.secondLayout.findViewById(R.id.detailArticle);
        viewHolder.btnDetailOnMap = (Button) convertView.findViewById(R.id.detailOnMap);
        viewHolder.btnDetailJoin = (Button) convertView.findViewById(R.id.detailJoin);

        //데이터 하나 뽑은 후
        final OpenMatchDTO openMatchDTO = (OpenMatchDTO) list.get(position);

        viewHolder.btnDetailOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double lat = openMatchDTO.getLat();
                Double lng = openMatchDTO.getLng();
                Intent intent = new Intent(getContext(), PopupMapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("type", false);
                getContext().startActivity(intent);
            }
        });
        viewHolder.btnDetailJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //참여 처리
                MatchingDTO matchingDTO = new MatchingDTO();
                matchingDTO.setOpenMatchId(openMatchDTO.getId());
                Long userIdx = Long.valueOf(preferenceUtil.getString("userIdx"));
                // -1이면 조회되지 않는 유저임. 참가로직 안돌도록
                if(userIdx == -1l){
                    return;
                }
                matchingDTO.setUserIndex(userIdx);
                retrofitUtil.getRetrofitAPI().joinMatch(matchingDTO).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "참여 완료", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext, "응답 없음", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            }
        });

        viewHolder.tvSubect.setText(openMatchDTO.getSubject());
        viewHolder.tvSportType.setText(openMatchDTO.getSportType());
        viewHolder.tvPersonnel.setText(String.valueOf("인원:" + "??/" + openMatchDTO.getPersonnel()));
        viewHolder.tvPlayDateTime.setText(String.valueOf(openMatchDTO.getPlayDateTime()));
        if(!preferenceUtil.getString("lat").equals("") && !preferenceUtil.getString("lng").equals("")){
            viewHolder.myLat = Double.valueOf(preferenceUtil.getString("lat"));
            viewHolder.myLng = Double.valueOf(preferenceUtil.getString("lng"));
        }


        //디테일 부분
        // 상세내용
        Log.d("디테일", "아티클: " + openMatchDTO.getArticle());
        if(openMatchDTO.getArticle() == null || openMatchDTO.getArticle().equals("")){
            viewHolder.tvArticle.setText("상세 내용 없음");
        }else {
            viewHolder.tvArticle.setText(openMatchDTO.getArticle());
        }
        //지도 내용 없으면 버튼 비활성화
        if(openMatchDTO.getLat() == null || openMatchDTO.getLng() == null){
            viewHolder.btnDetailOnMap.setEnabled(false);
            viewHolder.btnDetailOnMap.setText("위치 미정");
        }else {
            viewHolder.mapLat = openMatchDTO.getLat();
            viewHolder.mapLng = openMatchDTO.getLng();

            viewHolder.distanceToMe = computeDistance(viewHolder.myLat, viewHolder.myLng, viewHolder.mapLat, viewHolder.mapLng);
            viewHolder.tvDistanceToMe.setText("나와의 거리: " + String.valueOf(viewHolder.distanceToMe));
        }

        loadAllProfileThisMatch(viewHolder, openMatchDTO.getId());
        //스몰 프로필 부분
        viewHolder.profileDTOs = new ArrayList<>();
        viewHolder.customListView = (ListView) convertView.findViewById(R.id.detailProfileList);


        return convertView;

    }

    private int computeDistance(Double myLat, Double myLng, Double mapLat, Double mapLng){

        Double R = 6372.8 * 1000;

        Double dLat = Math.toRadians(mapLat - myLat);
        Double dLng = Math.toRadians(mapLng - myLng);
        Double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLng / 2), 2) * Math.cos(Math.toRadians(myLat)) * Math.cos(Math.toRadians(mapLat));
        Double c = 2 * Math.asin(Math.sqrt(a));

        return (int) (R * c);
    }

    public void loadAllProfileThisMatch(ViewHolder viewHolder, Long id){

        retrofitUtil.getRetrofitAPI().getJoinedUserProfiles(id).enqueue(new Callback<List<ProfileDTO>>() {
            @Override
            public void onResponse(Call<List<ProfileDTO>> call, Response<List<ProfileDTO>> response) {
                if(response.isSuccessful()){
                    viewHolder.profileDTOs = (ArrayList<ProfileDTO>) response.body();
                    viewHolder.smallProfileAdapter = new SmallProfileAdapter(getContext(), viewHolder.profileDTOs);
                    viewHolder.customListView.setAdapter(viewHolder.smallProfileAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProfileDTO>> call, Throwable t) {

            }
        });

    }

}
