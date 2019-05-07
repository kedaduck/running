package com.ding.running.Activity.scenic;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ding.running.Activity.scenic.adapter.AttractionAdapter;
import com.ding.running.Activity.scenic.adapter.GoodAdapter;
import com.ding.running.Common.Const;
import com.ding.running.Common.ResponseCode;
import com.ding.running.Common.ServerResponse;
import com.ding.running.Common.UrlContent;
import com.ding.running.Common.iBeaconClass;
import com.ding.running.MyViews.ProgressDialogUtil;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.Utils.OkHttp.CommonOkHttpClient;
import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;
import com.ding.running.Utils.OkHttp.request.CommonRequest;
import com.ding.running.vo.AttractionVo;
import com.ding.running.vo.GoodVo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static com.ding.running.MyViews.ProgressDialogUtil.closeProgressDialog;

/**
 * @ClassName GoodFragment
 * @Author Leoren
 * @Date 2019/5/6 11:35
 * Description :
 * @Version v1.0
 */
public class GoodFragment extends Fragment {

    private static final String TAG = "GoodFragment";

    private View rootView;

    private RecyclerView goodListView;
    private List<GoodVo> goodList;
    private GoodAdapter adapter;

    private TextView errorText;
    private int type;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;
    // iBeacon设备扫描回调结果
    @SuppressLint("NewApi")
    public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            getAttractionType(ibeacon);
            initData();
            Log.e(TAG, type + "type ============== ");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        Thread.sleep(20000);
                        mBluetoothAdapter.startLeScan(mLeScanCallback);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBlueTooth();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_good, container, false);

        requestAttractionData();
        initViews();

        return rootView;
    }

    private void initData(){
        if(type == Const.AttractionType.ATTRACTION_TYPE){
            requestAttractionData();
            if(goodListView.getVisibility() == View.GONE){
                goodListView.setVisibility(View.VISIBLE);
            }
            if(errorText.getVisibility() ==View.VISIBLE){
                errorText.setVisibility(View.GONE);
            }
        }else {
            if(goodListView.getVisibility() == View.VISIBLE){
                goodListView.setVisibility(View.GONE);
            }
            if(errorText.getVisibility() ==View.GONE){
                errorText.setVisibility(View.VISIBLE);
            }
        }
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

    private void requestAttractionData(){
        ProgressDialogUtil.showProgressDialog(getActivity());
        if(goodList == null){
            goodList = new ArrayList<>();
        }
        Request request = CommonRequest.createGetRequest(UrlContent.FIND_ALL_GOOD);
        CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(final String responseStr) {
                closeProgressDialog();
                ServerResponse<List<GoodVo>> response = GsonUtil.formatJsonToGoodList(responseStr);
                if(response == null){
                    onFailure(new OkHttpException(ResponseCode.NETWORK_ERROR.getCode(), ResponseCode.NETWORK_ERROR.getValue()));
                    return;
                }
                if (response.getStatus() == ResponseCode.SUCCESS.getCode()) {
                    goodList = response.getData();
                    if(goodList == null || goodList.size() <= 0){
                        onFailure(new OkHttpException(ResponseCode.ERROR.getCode(), response.getMsg()));
                        return;
                    }
                    initListView();
                }
                ToastUtil.MakeToast(response.getMsg());
            }

            @Override
            public void onFailure(OkHttpException e) {
                closeProgressDialog();
                ToastUtil.MakeToast(e.getMsg() + "");

            }
        }));

    }

    private void initViews() {
        errorText = rootView.findViewById(R.id.good_error_text);
        goodListView = rootView.findViewById(R.id.good_list_view);
    }

    private void initListView(){
        adapter = new GoodAdapter(goodList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        goodListView.setLayoutManager(manager);
        goodListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }
}
