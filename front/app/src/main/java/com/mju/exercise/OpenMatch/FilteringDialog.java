package com.mju.exercise.OpenMatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mju.exercise.R;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.List;

public class FilteringDialog extends BottomSheetDialogFragment {

    private ExpandableLayout exlayoutDistance, exlayoutDay, exlayoutPerssonel;
    private Button btnFilterApply;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_filtering, container, false);
        btnFilterApply = (Button) view.findViewById(R.id.btnFilterApply);
        btnFilterApply.setOnClickListener(setOnClickListener);

        exlayoutDistance = (ExpandableLayout) view.findViewById(R.id.exlayoutDistance);
        exlayoutDay = (ExpandableLayout) view.findViewById(R.id.exlayoutDay);
        exlayoutPerssonel = (ExpandableLayout) view.findViewById(R.id.exlayoutPerssonel);

        initExpandableLayout(exlayoutDistance);
        initExpandableLayout(exlayoutDay);
        initExpandableLayout(exlayoutPerssonel);

        return view;
    }

    private void initExpandableLayout(ExpandableLayout expandableLayout){
        TextView tvFilterType = (TextView) expandableLayout.parentLayout.findViewById(R.id.tvFilterType);
        Button btnFilterDetail = (Button) expandableLayout.parentLayout.findViewById(R.id.btnFilterDetail);
        btnFilterDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandableLayout.isExpanded()){
                    expandableLayout.collapse();
                }else {
                    expandableLayout.expand();
                }
            }
        });

        ChipGroup chipGroup = (ChipGroup) expandableLayout.secondLayout.findViewById(R.id.chipGroupFilter);
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

            }
        });

        switch (expandableLayout.getId()){
            case R.id.exlayoutDistance:
                tvFilterType.setText("거리 필터");
                String[] distance = {
                        "가까운 거리순", "100m 이내", "500m 이내","1km 이내","3km 이내","3km 초과"
                };
                for(String tmp: distance){
                    chipGroup.addView(makeChip(tmp));
                }
                break;
            case R.id.exlayoutDay:
                tvFilterType.setText("날짜 필터");
                String[] day = {
                        "가까운 날짜순", "요일 선택", "특정 날짜 선택"
                };
                for(String tmp: day){
                    chipGroup.addView(makeChip(tmp));
                }
                break;
            case R.id.exlayoutPerssonel:
                tvFilterType.setText("인원 필터");
                String[] personnel = {
                        "모두 보기", "참여 가능", "자리 없음"
                };
                for(String tmp: personnel){
                    chipGroup.addView(makeChip(tmp));
                }
                break;
        }

    }

    //칩 안에 넣을 스트링 전달후 칩 만들어서 리턴
    private Chip makeChip(String chipText){
        Chip chip = new Chip(getContext()); // Must contain context in parameter
        chip.setText(chipText);
        chip.setCheckable(false);

        return chip;
    }

    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btnFilterApply:
                    dismiss();
                    break;
            }

        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        // id = android.support.design.R.id.design_bottom_sheet for support librares
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private int getBottomSheetDialogDefaultHeight() { return getWindowHeight() * 75 / 100; }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
