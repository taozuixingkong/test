package com.liuleilei.macbook.mydemo.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liuleilei.macbook.basedispose.util.ToastUtil;
import com.liuleilei.macbook.mydemo.R;
import com.liuleilei.macbook.mydemo.adapter.MainActivityAdapter;
import com.liuleilei.macbook.mydemo.inter.RecyclerInterface;
import com.liuleilei.macbook.mydemo.kotlinactivity.ConstraintLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerInterface {

    private RecyclerView myRecyclerView;
    private List<String> list;
    private MainActivityAdapter mainActivityAdapter;
    public static List<String> logList = new CopyOnWriteArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = findViewById(R.id.my_recycler_view);
        initData();
        initEvents();
    }

    private void initData() {
        list = new ArrayList<>();
        list.clear();
        list.add("分发");
        list.add("约束动画");
        list.add("radioGroup");
        list.add("tts");
        list.add("下载");
        list.add("https");
        list.add("约束布局");
        list.add("分发");
    }

    private void initEvents() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 5);
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint mPaint;
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mPaint.setColor(Color.parseColor("#ff0000"));
                mPaint.setStyle(Paint.Style.FILL);
                final int left = parent.getPaddingLeft();
                final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
                final int childSize = parent.getChildCount();
                for (int i = 0; i < childSize; i++) {
                    final View child = parent.getChildAt(i);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + layoutParams.bottomMargin;
                    final int bottom = top + 5;
                    if (mPaint != null) {
                        c.drawRect(left, top, right, bottom, mPaint);
                    }
                }
            }
        });
        mainActivityAdapter = new MainActivityAdapter(this, list);
        myRecyclerView.setAdapter(mainActivityAdapter);
        mainActivityAdapter.setRecyclerInterface(this);
    }

    @Override
    public void onItemListener(int position) {
        ToastUtil.showToastShort(this,list.get(position));
        switch (list.get(position)) {
            case "分发":
                break;
            case "约束动画":
                ConstraintAnimationActivity.start(this);
                break;
            case "radioGroup":
                RadioGroupTestActivity.start(this);
                break;
            case "tts":
                TextToSpeechActivity.start(this);
                break;
            case "下载":
                DownLoadActivity.start(this);
                break;
            case "https":
                 HttpUrlActivity.start(this);
                break;
            case "约束布局":
                ConstraintLayoutActivity.Companion.start(this);
            default:
                break;
        }

    }
    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        //mLogView.setText(AllLog);
    }


}
