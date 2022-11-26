package com.mju.exercise;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.mju.exercise.OpenMatch.OpenMatchOpenFrag;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;

public class PopupMapActivity extends AppCompatActivity implements NaverMap.OnMapClickListener, Overlay.OnClickListener, OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener {

    private MapView mapView;
    private static NaverMap naverMap;
    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationSource locationSource;

    //액티비티 리턴용 위경도 값
    private Double lat;
    private Double lng;
    //액티비티 리턴용 해당 위치 값
    private String strRegion;

    //오픈매치 생성시 지도를 연 것인지, 아니면 지도에서 보기로 단순 확인인지 구분용
    private boolean isMake;

    //경기장소 선택완료 처리용
    ExtendedFloatingActionButton btnSelectOk, btnClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);

        Intent intent = getIntent();
        isMake = intent.getBooleanExtra("type", true);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.popupMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // true면 오픈매치 생성할때 연거임
        if(isMake){
            btnSelectOk = (ExtendedFloatingActionButton) findViewById(R.id.btnSelectOk);
            btnSelectOk.setOnClickListener(setOnClickListener);
        }else {
            lat = intent.getDoubleExtra("lat", 0.0);
            lng = intent.getDoubleExtra("lng", 0.0);

            btnClose = (ExtendedFloatingActionButton) findViewById(R.id.btnSelectOk);
            btnClose.setOnClickListener(setOnClickListener);

            btnClose.setText("닫기");
        }

    }

    private View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == btnSelectOk){
                Intent intent = new Intent(PopupMapActivity.this, OpenMatchOpenFrag.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("strRegion", strRegion);
                setResult(RESULT_OK, intent);
                finish();
            }else if(v==btnClose){
                finish();
            }

        }
    };

    private List<Marker> markerList = new ArrayList<>();

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        locationSource = new FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        if(isMake){
            //사용자가 해당 위치 클릭시 마커 생성하고 위경도 값 담음
            naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                    //만약 기존에 찍힌 마커가 있다면 삭제해버림
                    //운동장소 한곳만 선택되는것처럼 보이도록
                    if(markerList.size() > 0){
                        Marker marker = markerList.remove(markerList.size() - 1);
                        marker.setMap(null);
                    }

                    Marker marker = new Marker();
                    marker.setPosition(latLng);
                    //마커 크기, 색상 설정
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.RED);
                    marker.setMap(naverMap);
                    markerList.add(marker);

                    lat = latLng.latitude;
                    lng = latLng.longitude;
                }
            });
        }else {
            LatLng latLng = new LatLng(lat, lng);
            Marker marker = new Marker();
            marker.setPosition(latLng);
            marker.setMap(naverMap);

            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(
                    latLng, 15)
                    .animate(CameraAnimation.Fly, 1500);
            naverMap.moveCamera(cameraUpdate);
        }


    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



    //팝업으로 띄울때 화면 밖 터치해도 닫히지 않도록 함
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onCameraChange(int i, boolean b) {

    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

    }



    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    @Override
    public boolean onClick(@NonNull Overlay overlay) {

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ACCESS_LOCATION_PERMISSION_REQUEST_CODE:
                locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }
}
