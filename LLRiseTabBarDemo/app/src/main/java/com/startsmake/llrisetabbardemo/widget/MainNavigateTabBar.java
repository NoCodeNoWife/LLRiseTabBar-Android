package com.startsmake.llrisetabbardemo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.startsmake.llrisetabbardemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User:Shine
 * Date:2015-10-19
 * Description:
 */
public class MainNavigateTabBar extends LinearLayout implements View.OnClickListener {

    private List<ViewHolder> mViewHolderList;
    private String mCurrSelectedTag;
    private OnTabSelectedListener mOnTabSelectedListener;

    public MainNavigateTabBar(Context context) {
        this(context, null);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewHolderList = new ArrayList<>();
    }


    public void addTab(String tag, TabParam tabParam) {
        int defaultLayout = R.layout.comui_tab_view;
        if (tabParam.tabViewResId > 0) {
            defaultLayout = tabParam.tabViewResId;
        }
        View view = LayoutInflater.from(getContext()).inflate(defaultLayout, null);
        view.setFocusable(true);

        ViewHolder holder = new ViewHolder();

        holder.tag = tag;
        holder.pageParam = tabParam;

        holder.tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
        holder.tabTitle = ((TextView) view.findViewById(R.id.tab_title));

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.tabTitle.setVisibility(View.INVISIBLE);
        } else {
            holder.tabTitle.setText(tabParam.title);
        }

        if (tabParam.backgroundColor > 0) {
            view.setBackgroundResource(tabParam.backgroundColor);
        }

        if (tabParam.iconResId > 0) {
            holder.tabIcon.setImageResource(tabParam.iconResId);
        } else {
            holder.tabIcon.setVisibility(View.INVISIBLE);
        }

        if (tabParam.iconResId > 0 && tabParam.iconSelectedResId > 0) {
            view.setTag(tag);
            view.setOnClickListener(this);
            mViewHolderList.add(holder);
        }

        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));

    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag();
        if (object != null && object instanceof String && mOnTabSelectedListener != null) {
            mOnTabSelectedListener.onTabSelected((String) object);
        }
    }

    public void setCurrSelectedByTag(String tag) {
        if (TextUtils.equals(mCurrSelectedTag, tag)) {
            return;
        }
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(mCurrSelectedTag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconResId);
            } else if (TextUtils.equals(tag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconSelectedResId);
            }
        }
        mCurrSelectedTag = tag;
    }


    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
    }

    private static class ViewHolder {
        public String tag;
        public TabParam pageParam;
        public ImageView tabIcon;
        public TextView tabTitle;
    }


    public static class TabParam {
        public int backgroundColor = android.R.color.white;
        public int iconResId;
        public int iconSelectedResId;
        public int tabViewResId;
        public String title;
    }


    public interface OnTabSelectedListener {
        void onTabSelected(String tag);
    }

}
