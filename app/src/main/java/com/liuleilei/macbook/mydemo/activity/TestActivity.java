package com.liuleilei.macbook.mydemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;

import com.liuleilei.macbook.basedispose.base.BaseActivity;
import com.zs.border.view.BorderTextView;

/**
 * create by liu
 * on2019/7/24
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    private void initData() {
        GestureDetector gestureDetector = new GestureDetector(this, (GestureDetector.OnGestureListener) this);
        BorderTextView borderTextView;
    }
}
