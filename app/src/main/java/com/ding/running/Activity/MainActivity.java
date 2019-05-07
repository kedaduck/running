package com.ding.running.Activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ding.running.Activity.Fragment.FindPeopleFragment;
import com.ding.running.Activity.Fragment.LocationFragment;
import com.ding.running.Activity.Fragment.MySelfFragment;
import com.ding.running.Activity.Fragment.ScenicFragment;
import com.ding.running.R;

import java.time.chrono.MinguoEra;

public class MainActivity extends AppCompatActivity {

    private Fragment scenicFragment;
    private Fragment findPeopleFragment;
    private Fragment locationFragment;
    private Fragment mineFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private BottomNavigationView mainNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        setSelectView(0);
    }

    //初始化界面
    private void initLayout(){

        manager = getSupportFragmentManager();


        mainNavView = findViewById(R.id.nav_view);
        mainNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_scenic:
                        setSelectView(0);
                        return true;
                    case R.id.navigation_find:
                        setSelectView(1);
                        return true;
                    case R.id.navigation_location:
                        setSelectView(2);
                        return true;
                    case R.id.navigation_mine:
                        setSelectView(3);
                        return true;
                }
                return false;
            }
        });


    }

    //设置选中事件
    private void setSelectView(int position){
        transaction = manager.beginTransaction();

        hideFragment(transaction);
        switch (position){
            case 0:
                if(scenicFragment == null){
                    scenicFragment = new ScenicFragment();
                    transaction.replace(R.id.main_content, scenicFragment);
                }else {
                    transaction.show(scenicFragment);
                }
                break;
            case 1:
                if(findPeopleFragment == null){
                    findPeopleFragment = new FindPeopleFragment();
                    transaction.add(R.id.main_content, findPeopleFragment);
                }else {
                    transaction.show(findPeopleFragment);
                }
                break;
            case 2:
                if(locationFragment == null){
                    locationFragment = new LocationFragment();
                    transaction.add(R.id.main_content, locationFragment);
                }else {
                    transaction.show(locationFragment);
                }
                break;
            case 3:
                if(mineFragment == null){
                    mineFragment = new MySelfFragment();
                    transaction.add(R.id.main_content, mineFragment);
                }else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(scenicFragment != null){
            transaction.hide(scenicFragment);
        }
        if(findPeopleFragment != null){
            transaction.hide(findPeopleFragment);
        }
        if(locationFragment != null){
            transaction.hide(locationFragment);
        }
        if(mineFragment != null){
            transaction.hide(mineFragment);
        }
    }



}
