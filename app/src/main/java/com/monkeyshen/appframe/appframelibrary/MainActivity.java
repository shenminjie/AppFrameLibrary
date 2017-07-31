package com.monkeyshen.appframe.appframelibrary;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.monkeyshen.appframe.appframelibrary.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

//    @BindView(R.id.btn)
//    Button btn;

    @Override
    public void initView() {
        setToolbarTitle("good");
    }

    @Override
    public int getContentViewResource() {
        return R.layout.activity_main;
    }

//    @OnClick(R.id.btn)
//    public void onClick(View view){
//        Log.e("tag", view + "");
//    }


}
