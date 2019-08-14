package com.liuleilei.macbook.mydemo.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuleilei.macbook.mydemo.R;
import com.liuleilei.macbook.mydemo.adapter.MainActivityAdapter;
import com.liuleilei.macbook.mydemo.inter.RecyclerInterface;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * create by liu
 * on2019/7/23
 */
public class HomeFragment extends Fragment implements RecyclerInterface {
    private View rootView;
    private RecyclerView myRecyclerView;
    private MainActivityAdapter mainActivityAdapter;
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = View.inflate(getContext(), R.layout.fragment_home, null);
            myRecyclerView = rootView.findViewById(R.id.my_recycler_view);
        }
        initData();
        return rootView;
    }

    private void initData() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        list.clear();
        list.add("toast");
        mainActivityAdapter = new MainActivityAdapter(getContext(), list);
        myRecyclerView.setAdapter(mainActivityAdapter);
        mainActivityAdapter.setRecyclerInterface(this);

    }

    @Override
    public void onItemListener(int position) {
        switch (list.get(position)) {
            case "toast":
                Toasty.error(getContext(), "success").show();
                break;
            default:
                break;
        }
    }
}
