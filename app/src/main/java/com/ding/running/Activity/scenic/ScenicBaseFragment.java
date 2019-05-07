package com.ding.running.Activity.scenic;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ding.running.Common.Const;
import com.ding.running.Common.iBeaconClass;
import com.ding.running.R;

import static android.support.constraint.Constraints.TAG;

/**
 * @ClassName ScenicBaseFragment
 * @Author Leoren
 * @Date 2019/5/6 17:30
 * Description :
 * @Version v1.0
 */
public class ScenicBaseFragment extends Fragment {

    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothManager bluetoothManager;

    public int type;

    // iBeacon设备扫描回调结果
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            getAttractionType(ibeacon);
            Log.e(TAG, type + "type ============== ");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBlueTooth();
    }

    public void initBlueTooth(){
        // 判断手机等设备是否支持BLE，即是否可以扫描iBeacon设备
        bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), "BLE is not supported", Toast.LENGTH_SHORT).show();
        }
        // 开启蓝牙
        mBluetoothAdapter.enable();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }


    @Override
    public void onResume() {
        super.onResume();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    /**
     * 模拟根据ibeacon信息从后台取对应商品据信息
     *
     * @param ibeacon
     * @return
     */
    public void getAttractionType(iBeaconClass.iBeacon ibeacon) {
        if(ibeacon == null){
            type = Const.AttractionType.UNKNOWN_TYPE;
            return;
        }
        if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid) && Const.MajorID.ATTRACTION_MAJOR_ID == ibeacon.major    // 这里是对照UUID，major,minor作为模拟唯一的识别id
                && 10111 == ibeacon.minor) {
            type = Const.AttractionType.ATTRACTION_TYPE;
        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid) && Const.MajorID.HOTEL_MAJOR_ID == ibeacon.major
                && 10111 == ibeacon.minor) {
            type = Const.AttractionType.HOTEL_TYPE;
        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid)
                && Const.MajorID.FOOD_MAJOR_ID == ibeacon.major && 10111 == ibeacon.minor) {
            type = Const.AttractionType.FOOD_TYPE;
        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.proximityUuid)
                && Const.MajorID.GOOD_MAJOR_ID == ibeacon.major && 10111 == ibeacon.minor) {
            type = Const.AttractionType.GOOD_TYPE;
        }else {
            type = Const.AttractionType.UNKNOWN_TYPE;
        }
    }
}
