package com.liuleilei.macbook.basedispose.base;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.liuleilei.macbook.basedispose.R;

/**
 * create by liu
 * on2019/6/25
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView leftTextView;
    public TextView titleTextView;
    public TextView rightTextView;
    public ConstraintLayout titleConstrainLayout;
    public FrameLayout contentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initBaseView();
        initBaseEvent();
    }

    private void initBaseEvent() {
        leftTextView.setOnClickListener(this);
        rightTextView.setOnClickListener(this);
    }

    /**
     * 初始化基本布局
     */
    private void initBaseView() {
        leftTextView = findViewById(R.id.left_text_view);
        titleTextView = findViewById(R.id.title_text_view);
        rightTextView = findViewById(R.id.right_text_view);
        titleConstrainLayout = findViewById(R.id.title_constrain_layout);
        contentLayout = findViewById(R.id.content_layout);
    }

    /**
     * 添加子布局
     *
     * @param layoutId
     */
    protected void addView(@LayoutRes int layoutId) {
        contentLayout.addView(LayoutInflater.from(this).inflate(layoutId, null));
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColor(this, color, statusBarAlpha);
    }

    /**
     * 设置状态栏全透明
     */
    public void setStatusBarTransparent() {
        StatusBarUtil.setTransparent(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.left_text_view){
            onGoBack();
        }else if(v.getId() == R.id.right_text_view){
            nextStep();
        }
    }

    /**
     * next
     */
    public void nextStep() {

    }

    /**
     * return
     */
    public void onGoBack() {
        finish();
    }
}
