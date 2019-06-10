package com.ding.running.Activity.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName LocationFragment
 * @Author Leoren
 * @Date 2019/5/6 7:08
 * Description :
 * @Version v1.0
 */
public class LocationFragment extends Fragment implements View.OnClickListener {

    private MapView playItemMapView;  // mapview
    private BaiduMap baiduMap;    //baiduMap 用来管理mapview
    LocationClient locationClient;
    private LocationClientOption option;
    public MyLocationListener myLocationListener = new MyLocationListener();

    private TextView positionText;

    private boolean isFirstLocate = true;

    private TextToSpeech tts;

    private View rootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location, container, false);
        requestPermission();
        playItemMapView = rootView.findViewById(R.id.my_location_map);
        positionText = rootView.findViewById(R.id.position_text);
        positionText.setOnClickListener(this);
        baiduMap = playItemMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getActivity());
        locationClient.registerLocationListener(myLocationListener);
        option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        locationClient.start();
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == tts.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initLocation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    private void requestPermission(){
        List<String> premissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!premissionList.isEmpty()){
            String[] permission = premissionList.toArray(new String[premissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),  permission, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            ToastUtil.MakeToast("必须由此权限才能使用本程序");
                            getActivity().finish();
                            return;
                        }
                    }
                }else {
                    ToastUtil.MakeToast("发生未知错误");
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        playItemMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        playItemMapView.onPause();
    }

    @Override
    public void onDestroy() {
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        playItemMapView.onDestroy();
        playItemMapView = null;
        super.onDestroy();

    }



    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || playItemMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 设置地图中心点
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(new MapStatus.Builder().target(latLng)
                            .overlook(-15).build());
            baiduMap.setMapStatus(mapStatusUpdate);
            String country = location.getCountry();
            String province = location.getProvince();
            String city = location.getCity();
            String street = location.getStreet();
            StringBuilder sb = new StringBuilder();
            sb.append(country).append(province).append(city).append(street);
            positionText.setText(location.getAddrStr());
            if (isFirstLocate) {
                isFirstLocate = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomBy(12f);
                baiduMap.animateMapStatus(update);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.position_text:
                playPositionData();
                break;
            default:
                break;
        }
    }

    private void playPositionData(){
        Log.e("Login", positionText.getText().toString());
        String locationStr = positionText.getText().toString();
        if(StringUtils.isBlank(locationStr)){
            positionText.setText("中国山西省太原市尖草坪区行知西路");
        }
        tts.speak("您当前的位置是 " + positionText.getText().toString(), TextToSpeech.QUEUE_ADD, null);
    }
}
