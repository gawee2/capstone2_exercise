package com.mju.exercise.OpenMatch;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.PopupMapActivity;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.R;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

public class OpenMatchOpenFrag extends Fragment {

    Button btnCreate, btnDatePickOpen, btnPersonnelPickOpen, btnMapPickOpen;
    TextInputEditText edtSubject, edtArticle;
    PreferenceUtil preferenceUtil;

    TextView tvPersonnel, tvDay;

    ChipGroup chipGroup;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_match_open, container, false);

        btnCreate = view.findViewById(R.id.btnCreate);
        btnDatePickOpen = view.findViewById(R.id.btnDatePickOpen);
        btnPersonnelPickOpen = view.findViewById(R.id.btnPersonnelPickOpen);
        btnMapPickOpen = view.findViewById(R.id.btnMapPickOpen);

        tvDay = view.findViewById(R.id.tvDay);
        tvPersonnel = view.findViewById(R.id.tvPersonnel);


        edtSubject = view.findViewById(R.id.edtSubject);
        edtArticle = view.findViewById(R.id.edtArticle);


        btnCreate.setOnClickListener(setOnClickListener);

        btnDatePickOpen.setOnClickListener(setOnClickListener);
        btnPersonnelPickOpen.setOnClickListener(setOnClickListener);
        btnMapPickOpen.setOnClickListener(setOnClickListener);

        preferenceUtil = PreferenceUtil.getInstance(getContext());

        //작업중, 현재 단말기 없어서 테스트는 못함
        chipGroup = view.findViewById(R.id.chipGroup);





        return view;
    }

    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnCreate:

                    if(!createOpenMatch()){
                        Toast.makeText(getContext(), "생성 실패", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Toast.makeText(getContext(), "오픈매치 생성 완료", Toast.LENGTH_SHORT).show();
                    break;


                case R.id.btnMapPickOpen:
                    Intent intent = new Intent(getContext(), PopupMapActivity.class);
                    startActivity(intent);
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
                            Date date = new Date();
                            date.setTime(selection);

                            String dateString = simpleDateFormat.format(date);

                            tvDay.setText(dateString);
                        }
                    });

                    materialDatePicker.show(getChildFragmentManager(), "date");

                    break;

                case R.id.btnPersonnelPickOpen:
                    NumberPicker numberPicker = new NumberPicker(getContext());
                    numberPicker.setMinValue(1);
                    numberPicker.setMinValue(50);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("인원선택");
                    builder.setView(numberPicker);
                    builder.create();
                    builder.show();

                    break;
            }
        }
    };

    private boolean createOpenMatch(){

        OpenMatchDTO openMatchDTO = new OpenMatchDTO();

        openMatchDTO.setSubject(edtSubject.getText().toString());
        openMatchDTO.setArticle(edtArticle.getText().toString());
        openMatchDTO.setOpenTime(nowTime());
        openMatchDTO.setOpenUserId(preferenceUtil.getString("userId"));

        Integer personnel;

        String sportType;
        LocalDateTime playDateTime;
        Double lat;
        Double lng;


        return false;
    }

    private LocalDateTime nowTime(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now(Clock.systemDefaultZone());
        }
        //오레오 미만 기기에서는 지원하지 않는 다고 함.
        //나중에 추가 필요
        return null;
    }


//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
//                setupRatio(bottomSheetDialog);
//            }
//        });
//        return dialog;
//    }
//
//    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
//        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
//        // id = android.support.design.R.id.design_bottom_sheet for support librares
//        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//        layoutParams.height = getBottomSheetDialogDefaultHeight();
//        bottomSheet.setLayoutParams(layoutParams);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//    }
//    private int getBottomSheetDialogDefaultHeight() { return getWindowHeight() * 90 / 100; }
//    private int getWindowHeight() {
//        // Calculate window height for fullscreen use
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        dismiss();
//    }
}
