package com.mju.exercise;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserInfoActivity extends AppCompatActivity {
    private Context mContext;
    private ImageView mImgProfile, mImgViewEditProfile;
    private TextView mTxtUserName, mTxtAddress, mTxtFavoriteSport, mTxtProfileMsg;
    private FloatingActionButton mFbtnFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //컨텍스트 지정
        mContext = this;

        //초기화
        init();
    }

    /**
     * 초기화
     */
    private void init() {
        //프로필 사진
        mImgProfile = (ImageView) findViewById(R.id.imgProfile);
        //사용자 이름
        mTxtUserName = (TextView) findViewById(R.id.txtUserName);
        //지역
        mTxtAddress = (TextView) findViewById(R.id.txtAddress);
        //선호종목
        mTxtFavoriteSport = (TextView) findViewById(R.id.txtFavoriteSport);
        //상태 메시지
        mTxtProfileMsg = (TextView) findViewById(R.id.txtProfileMsg);
        //수정 버튼
        mImgViewEditProfile = (ImageView) findViewById(R.id.imgViewEditProfile);
        mImgViewEditProfile.setOnClickListener(setOnClickListener);
        //피드 추가 플로팅 버튼
        mFbtnFeed = (FloatingActionButton) findViewById(R.id.fbtnFeed);
        mFbtnFeed.setOnClickListener(setOnClickListener);
    }

    /**
     * 클릭 이벤트
     */
    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mImgViewEditProfile) {
                Toast.makeText(mContext, "수정", Toast.LENGTH_SHORT).show();
            }else if (v == mFbtnFeed){
                Toast.makeText(mContext, "추가", Toast.LENGTH_SHORT).show();
            }
        }
    };
}