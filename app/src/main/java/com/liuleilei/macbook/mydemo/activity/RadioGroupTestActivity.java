package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.liuleilei.macbook.mydemo.R;

/**
 * create by liu
 * on2019/6/19
 */
public class RadioGroupTestActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton tabHome;
    private RadioButton tabCourse;
    private RadioButton tabHomework;
    private RadioButton tabMe;
    private RadioGroup radiogroupTab;

    public static void start(Context context) {
        Intent starter = new Intent(context, RadioGroupTestActivity.class);
     //   starter.putExtra();
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group_test);
        tabHome = findViewById(R.id.tab_home);
        tabCourse = findViewById(R.id.tab_course);
        tabHomework = findViewById(R.id.tab_homework);
        tabMe = findViewById(R.id.tab_me);
        radiogroupTab = findViewById(R.id.radiogroup_tab);
        radiogroupTab.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tab_home:
//                _btn1.setChecked(true);
//                _btn2.setChecked(false);
//                _btn3.setChecked(false);
//                _btn4.setChecked(false);


                break;
            case R.id.tab_course:
//                _btn2.setChecked(true);
//                _btn3.setChecked(false);
//                _btn4.setChecked(false);
                break;
            case R.id.tab_homework:
//                _btn2.setChecked(false);
//                _btn3.setChecked(true);
//                _btn4.setChecked(false);

                break;
            case R.id.tab_me:
                // 断开课程页面的socket
//                if(GlobalManager.socket != null){
//                    GlobalManager.socket.disconnect();
//                    GlobalManager.socket = null;
//                }
//                _btn2.setChecked(false);
//                _btn3.setChecked(false);
//                _btn4.setChecked(true);
                break;
        }
    }
}
