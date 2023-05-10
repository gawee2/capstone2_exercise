package com.mju.exercise.OpenMatch;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.mju.exercise.Domain.MatchingDTO;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.PopupMapActivity;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.R;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenMatchOpenFrag extends Fragment {

    Button btnCreate, btnDatePickOpen, btnPersonnelPickOpen, btnMapPickOpen, btnTimePickOpen;
    TextInputEditText edtSubject, edtArticle, edtOpenMatchPw;

    //api관련
    PreferenceUtil preferenceUtil;
    RetrofitUtil retrofitUtil;

    TextView tvPersonnel, tvDay, tvRegion, tvTime;

    //운동타입
    ChipGroup chipGroup;
    String sportType;


    //지도 정보 리턴 받기위해
    ActivityResultLauncher<Intent> activityResultLauncher;
    Double lat, lng;
    //오픈매치 운동 모집 날짜
    LocalDateTime playDateTime;
    int year, month, day, hour, min;
    Integer personnel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_match_open, container, false);

        btnCreate = view.findViewById(R.id.btnCreate);
        btnDatePickOpen = view.findViewById(R.id.btnDatePickOpen);
        btnPersonnelPickOpen = view.findViewById(R.id.btnPersonnelPickOpen);
        btnMapPickOpen = view.findViewById(R.id.btnMapPickOpen);
        btnTimePickOpen = view.findViewById(R.id.btnTimePickOpen);

        tvDay = view.findViewById(R.id.tvDay);
        tvPersonnel = view.findViewById(R.id.tvPersonnel);
        tvRegion = view.findViewById(R.id.tvRegion);
        tvTime = view.findViewById(R.id.tvTime);


        edtSubject = view.findViewById(R.id.edtSubject);
        edtArticle = view.findViewById(R.id.edtArticle);
        edtOpenMatchPw = view.findViewById(R.id.edtOpenMatchPw);

        btnCreate.setOnClickListener(setOnClickListener);

        btnDatePickOpen.setOnClickListener(setOnClickListener);
        btnPersonnelPickOpen.setOnClickListener(setOnClickListener);
        btnMapPickOpen.setOnClickListener(setOnClickListener);
        btnTimePickOpen.setOnClickListener(setOnClickListener);

        preferenceUtil = PreferenceUtil.getInstance(getContext());
        retrofitUtil = RetrofitUtil.getInstance();
        retrofitUtil.setToken(preferenceUtil.getString("accessToken"));

        chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedStateChangeListener(setOnCheckedStateChangeListener);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                lat = result.getData().getDoubleExtra("lat", 0.0);
                lng = result.getData().getDoubleExtra("lng", 0.0);

                tvRegion.setText("장소 선택 완료");

            } else {

            }
        });

        return view;
    }

    //칩그룹 리스너
    private ChipGroup.OnCheckedStateChangeListener setOnCheckedStateChangeListener = new ChipGroup.OnCheckedStateChangeListener() {
        @Override
        public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
            int chk = group.getCheckedChipId();
            String tmp = sportTypeByChip(chk);
            if(tmp != null){
                sportType = tmp;
            }
        }
    };

    //칩 그룹에서 선택된것에 따라서 운동타입 str로 변환
    private String sportTypeByChip(int chk){
        String tmp = null;
        switch (chk){
            case R.id.chipSoccer:
                tmp = "축구";
                break;
            case R.id.chipFutsal:
                tmp = "풋살";
                break;
            case R.id.chipBasketball:
                tmp = "농구";
                break;
            case R.id.chipBaseball:
                tmp = "야구";
                break;
            case R.id.chipBadminton:
                tmp = "배드민턴";
                break;
            case R.id.chipCycle:
                tmp = "사이클";
                break;
        }
        return tmp;
    }

    //필수 값 체크
    private boolean emptyCheck(){
        if(edtSubject.getText().toString() == null || sportType == null || personnel == null){
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("필수 항목을 채워주세요.").setMessage("오픈매치 이름, 종목, 인원 수는 필수적으로 선택해야합니다.")
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
            return false;
        }
        return true;
    }

    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnCreate:
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("오픈매치 생성").setMessage("입력한 내용으로 오픈매치를 생성하시겠습니까?")
                            .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("생성하기", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(emptyCheck()){
                                        createOpenMatch();
                                    }
                                }
                            }).show();
                    break;


                case R.id.btnMapPickOpen:
                    Intent intent = new Intent(getContext(), PopupMapActivity.class);
                    activityResultLauncher.launch(intent);
                    break;

                case R.id.btnDatePickOpen:
                    MaterialDatePicker materialDatePicker;
                    materialDatePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("경기 날짜를 선택")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                            Date date = new Date(selection);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                playDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                                year = playDateTime.getYear();
                                month = playDateTime.getMonthValue();
                                day = playDateTime.getDayOfMonth();
                            }

                            String dateString = simpleDateFormat.format(date);

                            tvDay.setText(dateString);
                        }
                    });

                    materialDatePicker.show(getChildFragmentManager(), "date");

                    break;

                case R.id.btnPersonnelPickOpen:

                    NumberPicker numberPicker = new NumberPicker(getContext());
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(50);
                    numberPicker.setWrapSelectorWheel(false);
                    numberPicker.setValue(1);

                    new AlertDialog.Builder(getContext())
                            .setTitle("인원 선택")
                            .setView(numberPicker)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    personnel = numberPicker.getValue();
                                    tvPersonnel.setText(personnel.toString());
                                }
                            })
                            .show();
                    break;

                case R.id.btnTimePickOpen:

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            hour = i;
                            min = i1;
                            tvTime.setText(String.valueOf(i) + "시 " + String.valueOf(i1) + "분");
                        }
                    },0,0, false);
                    timePickerDialog.show();

                    break;

            }
        }
    };


    //오픈 매치 생성 완료 후 채워져있던 필드를 비움
    private void clearView(){
        tvPersonnel.setText("인원");
        tvDay.setText("날짜");
        tvRegion.setText("장소");
        tvTime.setText("시간");
        edtSubject.setText(null);
        edtArticle.setText(null);
        edtOpenMatchPw.setText(null);
    }

    private void createOpenMatch(){

        LocalDateTime nowTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nowTime = LocalDateTime.now(Clock.systemDefaultZone());
            nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        Log.d("날짜", nowTime.toString());

        OpenMatchDTO openMatchDTO = new OpenMatchDTO();
        openMatchDTO.setSubject(edtSubject.getText().toString());
        openMatchDTO.setArticle(edtArticle.getText().toString());
        openMatchDTO.setOpenUserId(preferenceUtil.getString("userId"));
        if(edtOpenMatchPw.getText().toString() != null && !edtOpenMatchPw.getText().toString().equals("")){
            openMatchDTO.setOpenMatchPw(Integer.parseInt(edtOpenMatchPw.getText().toString()));
        }

        //날짜 전송
        openMatchDTO.setOpenTime(nowTime.toString());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //날짜가 있을때
            if(month > 0){
                LocalDateTime playDateTime = LocalDateTime.of(year, month, day, hour, min, 0);
                Log.d("날짜", playDateTime.toString());
                playDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                openMatchDTO.setPlayTime(playDateTime.toString());
            }else {
                //날짜가 없을때
                openMatchDTO.setPlayTime("");
            }

        }

        if(lat != null && lng != null){
            openMatchDTO.setLat(lat);
            openMatchDTO.setLng(lng);
        }
        if(sportType != null){
            openMatchDTO.setSportType(sportType);
        }
        if(personnel != null) {
            openMatchDTO.setPersonnel(personnel);
        }

        retrofitUtil.getRetrofitAPI().openMatch(openMatchDTO).enqueue(new Callback<OpenMatchDTO>() {
            @Override
            public void onResponse(Call<OpenMatchDTO> call, Response<OpenMatchDTO> response) {

                if(response.isSuccessful()){

                    OpenMatchDTO newOpenMatch = response.body();

                    //자신이 생성한 것은 참여처리 되어야 함
                    //참여 처리
                    MatchingDTO matchingDTO = new MatchingDTO();
                    matchingDTO.setOpenMatchId(newOpenMatch.getId());
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
                                new MaterialAlertDialogBuilder(getContext())
                                        .setTitle("생성 완료").setMessage("정상적으로 생성 완료되었습니다.")
                                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();
                                clearView();
                            }else {
                                new MaterialAlertDialogBuilder(getContext())
                                        .setTitle("오류 발생").setMessage("다시 시도해주세요.")
                                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {

                        }
                    });
                }else{
                    Log.d("날짜", "응답코드: " + String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<OpenMatchDTO> call, Throwable t) {
                Toast.makeText(getContext(), "오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
