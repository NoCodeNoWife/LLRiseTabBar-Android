package com.startsmake.llrisetabbardemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.startsmake.llrisetabbardemo.R;
import com.startsmake.llrisetabbardemo.fragment.CityFragment;
import com.startsmake.llrisetabbardemo.fragment.HomeFragment;
import com.startsmake.llrisetabbardemo.fragment.MessageFragment;
import com.startsmake.llrisetabbardemo.fragment.PersonFragment;
import com.startsmake.llrisetabbardemo.widget.MainNavigateTabBar;

public class MainActivity extends AppCompatActivity implements MainNavigateTabBar.OnTabSelectedListener {

    private static final String SAVE_INSTANCE_CURRENT_TAG = "com.startsmake.llrisetabbardemo.currentTag";

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_CITY = "同城";
    private static final String TAG_PAGE_PUBLISH = "发布";
    private static final String TAG_PAGE_MESSAGE = "消息";
    private static final String TAG_PAGE_PERSON = "我的";

    private String mCurrentTag;

    private MainNavigateTabBar mNavigateTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);
        initNavigateTabBar();

        hideAllFragment();

        // 默认显示首页
        String defaultTag = TAG_PAGE_HOME;
        if (savedInstanceState != null) {
            defaultTag = savedInstanceState.getString(SAVE_INSTANCE_CURRENT_TAG, defaultTag);
        }
        showFragment(defaultTag);
    }

    private void initNavigateTabBar() {
        MainNavigateTabBar.TabParam tabParam1 = new MainNavigateTabBar.TabParam();
        MainNavigateTabBar.TabParam tabParam2 = new MainNavigateTabBar.TabParam();
        MainNavigateTabBar.TabParam tabParam3 = new MainNavigateTabBar.TabParam();
        MainNavigateTabBar.TabParam tabParam4 = new MainNavigateTabBar.TabParam();
        MainNavigateTabBar.TabParam tabParam5 = new MainNavigateTabBar.TabParam();

        tabParam1.iconResId = R.mipmap.comui_tab_home;
        tabParam2.iconResId = R.mipmap.comui_tab_city;
        tabParam4.iconResId = R.mipmap.comui_tab_message;
        tabParam5.iconResId = R.mipmap.comui_tab_person;

        tabParam1.iconSelectedResId = R.mipmap.comui_tab_home_selected;
        tabParam2.iconSelectedResId = R.mipmap.comui_tab_city_selected;
        tabParam4.iconSelectedResId = R.mipmap.comui_tab_message_selected;
        tabParam5.iconSelectedResId = R.mipmap.comui_tab_person_selected;

        tabParam1.title = TAG_PAGE_HOME;
        tabParam2.title = TAG_PAGE_CITY;
        tabParam3.title = TAG_PAGE_PUBLISH;
        tabParam4.title = TAG_PAGE_MESSAGE;
        tabParam5.title = TAG_PAGE_PERSON;

        mNavigateTabBar.addTab(TAG_PAGE_HOME, tabParam1);
        mNavigateTabBar.addTab(TAG_PAGE_CITY, tabParam2);
        mNavigateTabBar.addTab(TAG_PAGE_PUBLISH, tabParam3);
        mNavigateTabBar.addTab(TAG_PAGE_MESSAGE, tabParam4);
        mNavigateTabBar.addTab(TAG_PAGE_PERSON, tabParam5);
        mNavigateTabBar.setOnTabSelectedListener(this);
    }


    /**
     * 显示 tag 对应的 fragment
     *
     * @param tag 要显示的 fragment 对应的 tag
     */
    private void showFragment(String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isFragmentShown(transaction, tag)) {
            return;
        }
        mNavigateTabBar.setCurrSelectedByTag(tag);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getFragmentInstance(tag);
            transaction.add(R.id.main_container, fragment, tag);
        } else {
            transaction.show(fragment);
        }

        transaction.commit();
    }

    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE_HOME);
        if (homeFragment != null && !homeFragment.isHidden()) {
            transaction.hide(homeFragment);
        }

        Fragment cityFragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE_CITY);
        if (cityFragment != null && !cityFragment.isHidden()) {
            transaction.hide(cityFragment);
        }

        Fragment messageFragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE_MESSAGE);
        if (messageFragment != null && !messageFragment.isHidden()) {
            transaction.hide(messageFragment);
        }

        Fragment personFragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE_PERSON);
        if (personFragment != null && !personFragment.isHidden()) {
            transaction.hide(personFragment);
        }

        transaction.commit();
    }


    /**
     * 判断要显示的fragment是否已经处于显示状态，不是的话会将之前的fragment隐藏
     *
     * @param transaction
     * @param newTag      要显示的fragment的标签
     * @return 已显示返回true, 否则返回false
     */
    private boolean isFragmentShown(FragmentTransaction transaction, String newTag) {
        if (newTag.equals(mCurrentTag)) {
            return true;
        }

        if (TextUtils.isEmpty(mCurrentTag)) {
            mCurrentTag = newTag;
            return false;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mCurrentTag);
        if (fragment != null && !fragment.isHidden()) {
            transaction.hide(fragment);
        }

        mCurrentTag = newTag;

        return false;
    }

    /**
     * 根据tag得到fragment实例
     *
     * @param tag fragment对于标签
     * @return
     */
    public Fragment getFragmentInstance(String tag) {
        Fragment fragment = null;

        if (TextUtils.equals(tag, TAG_PAGE_HOME)) {
            fragment = new HomeFragment();
        } else if (TextUtils.equals(tag, TAG_PAGE_CITY)) {
            fragment = new CityFragment();
        } else if (TextUtils.equals(tag, TAG_PAGE_MESSAGE)) {
            fragment = new MessageFragment();
        } else if (TextUtils.equals(tag, TAG_PAGE_PERSON)) {
            fragment = new PersonFragment();
        }
        return fragment;
    }


    @Override
    public void onTabSelected(String tag) {
        showFragment(tag);
    }

    public void onClickPost(View view) {
        Toast.makeText(this, "发布", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_INSTANCE_CURRENT_TAG, mCurrentTag);
    }
}
