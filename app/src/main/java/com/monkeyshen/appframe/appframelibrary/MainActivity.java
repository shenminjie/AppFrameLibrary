package com.monkeyshen.appframe.appframelibrary;

import android.support.design.widget.FloatingActionButton;
import android.widget.RelativeLayout;

import com.monkeyshen.appframe.appframelibrary.base.activity.BaseMvpActivity;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BasePresenter;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity {



    @Override
    public void initView() {

    }

    @Override
    public int getContentViewResource() {
        return 0;
    }


    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
