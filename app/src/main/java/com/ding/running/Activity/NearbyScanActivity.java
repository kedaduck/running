package com.ding.running.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.ding.running.Common.Commodity;
import com.ding.running.Common.CommodityAdapter;
import com.ding.running.Common.Const;
import com.ding.running.Common.iBeaconClass;
import com.ding.running.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NearbyScanActivity extends AppCompatActivity {

    private CommodityAdapter mLeDeviceListAdapter;
    private static final int PERMISSION_GRANTED = 0;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ListView lv_ommodity;
    private int type;

    // iBeacon设备扫描回调结果
    @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            final Commodity commodity = getCommodity(ibeacon);
            Log.e(TAG, type + "type ============== ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addCommodity(commodity);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };


    // 扫描频率
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_scan);

        lv_ommodity = (ListView)findViewById(R.id.lv_ommodity);


        // 判断手机等设备是否支持BLE，即是否可以扫描iBeacon设备
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }


        // 开启蓝牙
        mBluetoothAdapter.enable();

        //判断是否有权限
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //请求权限
//           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GRANTED);
//           //判断是否需要 向用户解释，为什么要申请该权限
//          if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                  Manifest.permission.READ_CONTACTS)) {
//              Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
//           }
//      }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        // scanLeDevice(true);
        mLeDeviceListAdapter = new CommodityAdapter(this);
        lv_ommodity.setAdapter(mLeDeviceListAdapter);

    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        mLeDeviceListAdapter.clear();
    }

    /**
     * 模拟根据ibeacon信息从后台取对应商品据信息
     *
     * @param ibeacon
     * @return
     */
    private Commodity getCommodity(iBeaconClass.iBeacon ibeacon) {
        if(ibeacon==null){
            type = -1;
            return  null;
        }

        Log.e("Running", "=======================");
        if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid) && 1001 == ibeacon.major    // 这里是对照UUID，major,minor作为模拟唯一的识别id
                && 10111 == ibeacon.minor) {
            type = Const.AttractionType.ATTRACTION_TYPE;
            return new Commodity("1", R.drawable.head_icon, 288.00, "太和殿，俗称金銮殿，是明清古代宫殿建筑，东方三大殿之一，是中国现存最大的木结构大殿。位于北京紫禁城（故宫）南北主轴线的显要位置，明永乐十八年（1420年）仿南京故宫奉天殿建成，称奉天殿。明嘉靖四十一年（1562年）改称皇极殿，清顺治二年（1645年）改今名。", ibeacon.distance);


        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid) && Const.AttractionType.HOTEL_TYPE== ibeacon.major
                && 10111 == ibeacon.minor) {
            type = Const.AttractionType.HOTEL_TYPE;
//            return new Commodity("258.00￥", R.drawable.head_icon, 258.00, "净味真烤羊腿套餐\n烤羊腿套餐,可使用包间", ibeacon.distance);

        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid)
                && Const.AttractionType.FOOD_TYPE == ibeacon.major && 10111 == ibeacon.minor) {
            type = Const.AttractionType.FOOD_TYPE;
            return new Commodity("399￥", R.drawable.head_icon, 258.00, "锦江之星\n入住送优惠券",
                    ibeacon.distance);
        }else {
            type = Const.AttractionType.GOOD_TYPE;
        }
        return null;
    }

    private void requestPermission(){
        List<String> premissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED) {
            premissionList.add(Manifest.permission_group.LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.BLUETOOTH_ADMIN);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.BLUETOOTH);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!premissionList.isEmpty()){
            String[] permission = premissionList.toArray(new String[premissionList.size()]);
            ActivityCompat.requestPermissions(this, permission, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}