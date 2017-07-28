package com.monkeyshen.appframe.appframelibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.RelativeLayout;

import com.monkeyshen.appframe.appframelibrary.base.activity.BaseActivity;
import com.monkeyshen.appframe.appframelibrary.base.activity.BaseMvpActivity;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity {


    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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
