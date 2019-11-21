package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liuleilei.macbook.mydemo.R;

public class ScrollingActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ScrollingActivity.class);
       // starter.putExtra();
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                testZhuanyi();
            }
        });
    }
    public void testZhuanyi() {
        String ss ="2019-3-8_1.txt";
        String[] segments = ss.split("\\.");
        System.out.println("segments:"+segments[0]);
        System.out.println("segments:"+segments.length);
        String[] fileSegments = segments[0].split("\\_");
        System.out.println("fileSegments:"+fileSegments[0]);
        System.out.println("fileSegments:"+fileSegments.length);
    }
}
