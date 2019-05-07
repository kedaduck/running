package com.ding.running.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ding.running.Activity.FindPeople.FindPeopleDetail;
import com.ding.running.Activity.Fragment.adapter.FindPeopleAdapter;
import com.ding.running.Common.ResponseCode;
import com.ding.running.Common.ServerResponse;
import com.ding.running.Common.UrlContent;
import com.ding.running.DB.bean.User;
import com.ding.running.MyViews.ProgressDialogUtil;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.Utils.OkHttp.CommonOkHttpClient;
import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;
import com.ding.running.Utils.OkHttp.request.CommonRequest;
import com.ding.running.vo.FindPeopleVo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @ClassName HotelFragment
 * @Author Leoren
 * @Date 2019/5/6 7:06
 * Description :
 * @Version v1.0
 */
public class FindPeopleFragment extends Fragment {

    private View rootView;

    private Toolbar toolbar;

    private RecyclerView findPeopleListView;
    private FindPeopleAdapter adapter;
    private List<FindPeopleVo> findPeopleVoList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_people, container, false);

        requestFindPeopleData();
        initViews();

        return rootView;
    }

    private void requestFindPeopleData(){
        if(findPeopleVoList == null){
            findPeopleVoList = new ArrayList<>();
        }
        ProgressDialogUtil.showProgressDialog(getContext());
        final Request request = CommonRequest.createGetRequest(UrlContent.FIND_ALL_FINDING);
        CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(final String responseStr) {
                ProgressDialogUtil.closeProgressDialog();
                ServerResponse<List<FindPeopleVo>> response = GsonUtil.formatJsonToFindPeopleList(responseStr);
                if(response == null){
                    onFailure(new OkHttpException(ResponseCode.NETWORK_ERROR.getCode(), ResponseCode.NETWORK_ERROR.getValue()));
                    return;
                }
                if (response.isSuccess()) {
                    findPeopleVoList = response.getData();
                }
            }

            @Override
            public void onFailure(OkHttpException e) {
                ProgressDialogUtil.closeProgressDialog();
                ToastUtil.MakeToast(e.getMsg() + "");

            }
        }));
    }

    private void initViews(){
        toolbar = rootView.findViewById(R.id.find_people_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        findPeopleListView = rootView.findViewById(R.id.find_people_list_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new FindPeopleAdapter(findPeopleVoList, getActivity());
        findPeopleListView.setLayoutManager(manager);
        findPeopleListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.publish_find_people, menu);
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.publish_find_people:
                publishFindPeople();
                break;
        }
        return true;
    }

    private void publishFindPeople(){
        Intent intent = new Intent(getActivity(), FindPeopleDetail.class);
        startActivity(intent);
    }


}
