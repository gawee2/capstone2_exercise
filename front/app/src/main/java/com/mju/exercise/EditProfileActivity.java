package com.mju.exercise;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
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
import com.mju.exercise.HttpRequest.RetrofitAPI;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;


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
                if(imgUri != null){
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealFile(imgUri));
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", "test.jpg", requestFile);
                    retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.3:8080")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    retrofitAPI = retrofit.create(RetrofitAPI.class);

                    retrofitAPI.uploadImg(body).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful()){
                                Log.d("이미지", response.body().toString());
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("이미지", t.getMessage());
                        }
                    });

                }

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

    private File getRealFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        if(uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if(cursor == null || cursor.getColumnCount() <1 ) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String path = cursor.getString(column_index);

        if(cursor != null) {
            cursor.close();
            cursor = null;
        }

        return new File(path);
    }

}
