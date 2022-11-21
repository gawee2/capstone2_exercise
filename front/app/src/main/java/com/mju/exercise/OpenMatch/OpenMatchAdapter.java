package com.mju.exercise.OpenMatch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class OpenMatchAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List list;

    public OpenMatchAdapter(@NonNull Context context, @NonNull ArrayList list) {
        super(context, 0, list);
        this.mContext = context;
        this.list = list;
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

        public Button btnDetailOnMap, btnDetailJoin;

        public Double lat, lng;
        public int distance;
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

        //디테일 부분
        viewHolder.tvArticle = (TextView) expandableLayout.secondLayout.findViewById(R.id.detailArticle);
        viewHolder.btnDetailOnMap = (Button) convertView.findViewById(R.id.detailOnMap);
        viewHolder.btnDetailOnMap.setOnClickListener(setOnClickListener);
        viewHolder.btnDetailJoin = (Button) convertView.findViewById(R.id.detailJoin);
        viewHolder.btnDetailJoin.setOnClickListener(setOnClickListener);

        //데이터 하나 뽑은 후
        final OpenMatchDTO openMatchDTO = (OpenMatchDTO) list.get(position);

        viewHolder.tvSubect.setText(openMatchDTO.getSubject());
        viewHolder.tvSportType.setText(openMatchDTO.getSportType());
        viewHolder.tvPersonnel.setText(String.valueOf("인원:" + "??/" + openMatchDTO.getPersonnel()));
        viewHolder.tvPlayDateTime.setText(String.valueOf(openMatchDTO.getPlayDateTime()));

        //디테일 부분
        // 상세내용
        Log.d("디테일", "아티클: " + openMatchDTO.getArticle());
        if(openMatchDTO.getArticle() == null){
            viewHolder.tvArticle.setText("상세 내용 없음");
        }else {
            viewHolder.tvArticle.setText(openMatchDTO.getArticle());
        }
        //지도 내용 없으면 버튼 비활성화
        if(openMatchDTO.getLat() == null || openMatchDTO.getLng() == null){
            viewHolder.btnDetailOnMap.setEnabled(false);
            viewHolder.btnDetailOnMap.setText("위치 미정");
        }else {
            viewHolder.lat = openMatchDTO.getLat();
            viewHolder.lng = openMatchDTO.getLng();
        }



        return convertView;

    }

    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.detailOnMap:
                    Log.d("디테일", "지도 보기");

                    break;
                case R.id.detailJoin:
                    //참여 처리
                    break;
            }
        }
    };
}
