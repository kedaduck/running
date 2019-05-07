package com.ding.running.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ding.running.Activity.scenic.AttractionFragment;
import com.ding.running.Activity.scenic.GoodFragment;
import com.ding.running.Activity.scenic.HotelFragment;
import com.ding.running.Activity.scenic.RestaurantFragment;
import com.ding.running.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ScenicFragment
 * @Author Leoren
 * @Date 2019/5/6 7:06
 * Description :
 * @Version v1.0
 */
public class ScenicFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    //顶部导航栏
    private TextView attractionTab;
    private TextView hotelTab;
    private TextView restaurantTab;
    private TextView goodTab;

    private TextView[] tabTextViews;

    private ViewPager viewPager;

    private FragmentPagerAdapter adapter;

    private List<Fragment> views = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scenic, container, false);

        initViews();

        setSelectView(0);

        return rootView;
    }

    private void initViews(){
        attractionTab = rootView.findViewById(R.id.tab_attraction_text);
        hotelTab = rootView.findViewById(R.id.tab_hotel_text);
        restaurantTab = rootView.findViewById(R.id.tab_restaurant_text);
        goodTab = rootView.findViewById(R.id.tab_good_text);

        attractionTab.setOnClickListener(this);
        hotelTab.setOnClickListener(this);
        restaurantTab.setOnClickListener(this);
        goodTab.setOnClickListener(this);

        tabTextViews = new TextView[]{attractionTab, hotelTab, restaurantTab, goodTab};

        viewPager = rootView.findViewById(R.id.scenic_content_pager);

        AttractionFragment attractionFragment = new AttractionFragment();
        HotelFragment hotelFragment = new HotelFragment();
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        GoodFragment goodFragment = new GoodFragment();

        views.add(attractionFragment);
        views.add(hotelFragment);
        views.add(restaurantFragment);
        views.add(goodFragment);
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return views.get(position);
            }

            @Override
            public int getCount() {
                return views.size();
            }
        };

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTabStyle();
                setSelectView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 重置导航标签的颜色和大小
     */
    private void resetTabStyle(){
        for(int i = 0 ; i <tabTextViews.length; i++){
            tabTextViews[i].setTextColor(getResources().getColor(R.color.light_black));
            tabTextViews[i].setTextSize(20);
        }
    }

    private void setSelectView(int position){
        tabTextViews[position].setTextSize(23);
        tabTextViews[position].setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_attraction_text:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_hotel_text:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab_restaurant_text:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tab_good_text:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;

        }
    }
}
