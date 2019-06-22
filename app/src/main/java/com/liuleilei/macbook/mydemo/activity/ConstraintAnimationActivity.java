package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.liuleilei.macbook.mydemo.R;

/**
 * create by liu
 * on2019/6/14
 * 约束动画
 */
public class ConstraintAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private View view1;
    private View view2;
    private View view3;
    private ImageView arrowView;
    boolean isShow = true;
    private ConstraintLayout parentConstrainLayout;
    private ConstraintSet constraintSet;

    public static void start(Context context) {
        Intent starter = new Intent(context, ConstraintAnimationActivity.class);
        // starter.putExtra();
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrain_animation);
        parentConstrainLayout = findViewById(R.id.parent_constrain_layout);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        arrowView = findViewById(R.id.arrow_view);
        arrowView.setOnClickListener(this);
        view2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_view:
                rotateAnimation();
                break;
            case R.id.view2:
                arrowView.setRotation(180);
                break;
            default:
                break;
        }
    }

    /**
     * 旋转动画
     */
    private void rotateAnimation() {
        RotateAnimation rotate = new RotateAnimation(isShow ? 0 : 180, isShow ? 180 : 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(true);
        arrowView.startAnimation(rotate);
        isShow = !isShow;
        constraintSet = new ConstraintSet();
        ConstraintLayout parentLayout = (ConstraintLayout) parentConstrainLayout.getParent();
        constraintSet.clone(parentLayout);
        constraintSet.clear(R.id.parent_constrain_layout, ConstraintSet.TOP);
        constraintSet.connect(R.id.parent_constrain_layout, ConstraintSet.TOP, R.id.view4, ConstraintSet.BOTTOM, isShow ? 0 : 300);
        AutoTransition transition = new AutoTransition();
        transition.setDuration(200);
        TransitionManager.beginDelayedTransition(parentLayout, transition);
        constraintSet.applyTo(parentLayout);
    }

}
