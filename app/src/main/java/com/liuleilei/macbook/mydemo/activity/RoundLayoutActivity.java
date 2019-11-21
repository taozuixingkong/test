package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import com.liuleilei.macbook.basedispose.base.BaseActivity;
import com.liuleilei.macbook.basedispose.util.DensityUtil;
import com.liuleilei.macbook.basedispose.view.RoundedRect;
import com.liuleilei.macbook.mydemo.R;

/**
 * create by liu
 * on2019/11/21
 */
public class RoundLayoutActivity extends BaseActivity {
    private LinearLayout constrainLayout;
    private Context context;

    public static void start(Context context) {
        Intent starter = new Intent(context, RoundLayoutActivity.class);
        //  starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        addView(R.layout.activity_round_layout);
        setTitleTextView("圆角布局");
        initViews();
    }

    private void initViews() {
        constrainLayout = findViewById(R.id.constrain_layout);
        RoundedRect roundedRect = new RoundedRect(context,DensityUtil.dp2px(25),Color.parseColor("#ffcc00"),DensityUtil.dp2px(10),Color.parseColor("#00ff00"));
        roundedRect.setLayoutParams(new ConstraintLayout.LayoutParams(DensityUtil.dp2px(200),DensityUtil.dp2px(200)));
        constrainLayout.addView(roundedRect);
    }
}
