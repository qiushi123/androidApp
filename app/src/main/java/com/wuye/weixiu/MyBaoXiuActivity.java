package com.wuye.weixiu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wuye.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

public class MyBaoXiuActivity extends AppCompatActivity {
    private MagicIndicator indicator;
    private ViewPager vpContainer;

    private ArrayList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bao_xiu);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        Log.i("qcl0322", "onResume");

    }

    private void initView() {
        vpContainer = (ViewPager) findViewById(R.id.vp_container);
        indicator = (MagicIndicator) findViewById(R.id.indicator);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        //viewpager相关
        initIndicator();
        ViewPagerHelper.bind(indicator, vpContainer);
    }


    /**
     * 初始化指示器
     */
    //0待维修，1已接单，2已处理待支付，3已支付待评价，4已完成
    public void initIndicator() {
        tags = new ArrayList<>();
        tags.add("待维修");
        tags.add("已接单");
        tags.add("待支付");
        tags.add("待评价");
        tags.add("已完成");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tags == null ? 0 : tags.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(tags.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vpContainer.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        //动态生成所需fragment
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    /**
     * ViewPager的适配器
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }


        @Override
        public int getCount() {
            return tags.size();
        }
    }

    private Fragment createFragment(int position) {
        Fragment fragment = new WeiXiuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("weixiu_type", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
