package com.mju.exercise.Profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.Domain.ProfileDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.OpenMatch.OpenMatchAdapter;
import com.mju.exercise.R;

import java.util.ArrayList;
import java.util.List;

public class SmallProfileAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List list;
    private RetrofitUtil retrofitUtil;

    public SmallProfileAdapter(@NonNull Context context, @NonNull ArrayList list) {
        super(context, 0, list);
        this.mContext = context;
        this.list = list;

        retrofitUtil = RetrofitUtil.getInstance();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class ViewHolder{
        public TextView tvSmallProfileNickname;
        public ImageView imgSmallProfile;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.small_profile_item, parent, false);
        }

        viewHolder = new ViewHolder();

        viewHolder.tvSmallProfileNickname = (TextView) convertView.findViewById(R.id.tvSmallProfileNickname);
        viewHolder.imgSmallProfile = (ImageView) convertView.findViewById(R.id.imgSmallProfile);


        final ProfileDTO profileDTO = (ProfileDTO) list.get(position);

        //닉네임
        viewHolder.tvSmallProfileNickname.setText(profileDTO.getNickname());
        //프로필 사진
        String path = profileDTO.getImage();
        String url = retrofitUtil.getBASE_URL_NONE_SLASH() + path;
        Log.d("이미지로드", url);
        if(path != null && !path.equals("")){
            Glide.with(mContext).load(url).circleCrop().into(viewHolder.imgSmallProfile);
        }
        viewHolder.imgSmallProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "스몰프로필 눌림", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                intent.putExtra("userId", profileDTO.getUserID());
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
