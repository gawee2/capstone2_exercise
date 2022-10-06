package com.mju.exercise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mju.exercise.Domain.ProfileDTO;

public class EditProfileActivity extends AppCompatActivity {

    Button btnEnter;
    ImageView imgProfile;
    EditText edtNickname, edtProfileMsg;
    Spinner ddo, si, gu;
    CheckBox chkFavMon, chkFavTue, chkFavWed, chkFavThu, chkFavFri, chkFavSat, chkFavSun;
    CheckBox chkFavSoccer, chkFavFutsal, chkFavBaseball, chkFavBasketball, chkFavBadminton, chkFavCycle;

    //이미지 업로드 위해서 액티비티 결과값 체크
    ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imgUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
    }

    public void init(){
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(onClickListener);

        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(onClickListener);

        edtNickname = (EditText) findViewById(R.id.txtNickname);
        edtProfileMsg = (EditText) findViewById(R.id.txtProfileMsg);

        chkFavMon = (CheckBox) findViewById(R.id.chkFavMon);
        chkFavTue = (CheckBox) findViewById(R.id.chkFavTue);
        chkFavWed = (CheckBox) findViewById(R.id.chkFavWed);
        chkFavThu = (CheckBox) findViewById(R.id.chkFavThu);
        chkFavFri = (CheckBox) findViewById(R.id.chkFavFri);
        chkFavSat = (CheckBox) findViewById(R.id.chkFavSat);
        chkFavSun = (CheckBox) findViewById(R.id.chkFavSun);

        chkFavSoccer = (CheckBox) findViewById(R.id.chkFavSoccer);
        chkFavFutsal = (CheckBox) findViewById(R.id.chkFavFutsal);
        chkFavBaseball = (CheckBox) findViewById(R.id.chkFavBaseball);
        chkFavBasketball = (CheckBox) findViewById(R.id.chkFavBasketball);
        chkFavBadminton = (CheckBox) findViewById(R.id.chkFavBadminton);
        chkFavCycle = (CheckBox) findViewById(R.id.chkFavCycle);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK && result.getData().getData() != null){
                imgUri = result.getData().getData();
                Glide.with(this).load(imgUri).into(imgProfile);
            }else{

            }
        });

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == btnEnter) {
                //여기서 서버로 요청
                ProfileDTO profileDTO = new ProfileDTO();

                profileDTO.setNickname(edtNickname.getText().toString());
                profileDTO.setIntroduce(edtProfileMsg.getText().toString());

                //이미지 처리 하고

            //이미지 클릭했을때는 사진첩 열리면서 이미지 선택 가능하도록
            }else if(view == imgProfile){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                activityResultLauncher.launch(intent);

            }
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.chkFavMon:
                    break;
            }
        }
    };

}
