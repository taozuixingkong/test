package com.liuleilei.macbook.basedispose.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuleilei.macbook.basedispose.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends Adapter<VH> {

    /**
     * 最多一个header一个footer
     */
    private static final int TYPE_HEADER = -1;
    private static final int TYPE_NORMAL = -2;
    private static final int TYPE_FOOTER = -3;

    protected String blue_start = "<font color=\"#0677ee\">";
    protected String gray_start = "<font color=\"#191919\">";
    protected String red_start = "<font color=\"#fa5a4f\">";
    protected String end = "</font>";

    private View headerView;
    private FooterView footerView;

    protected Context mContext;
    protected List<T> mData;

    protected LayoutInflater mInflater;

    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void insert(T t) {
        mData.add(t);
        notifyItemInserted(getStart() + mData.size());
    }

    public void insertFirst(T t) {
        mData.add(0, t);
        notifyItemInserted(getStart());
    }

    public void remove(int position) {
        if (position >= 0 && position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(getStart() + position);
            if (position != mData.size()) {      // 这个判断的意义就是如果移除的是最后一个，就不用管它了，= =whatever
                notifyItemRangeChanged(getStart() + position, mData.size() - position);
            }
        }
    }

    /**
     * 清除list所有项
     */
    public void removeAll() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public void notifyItem(int position) {
        notifyItemChanged(getStart() + position);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    protected void setFooterView(FooterView footerView) {
        this.footerView = footerView;
    }

    public FooterView getFooterView() {
        return footerView;
    }

    public void setPullLoadEnabled(boolean enabled) {
        setFooterView(enabled ? new FooterView(mContext) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        if (position >= getAdapterItemCount()) {
            return null;
        }
        return mData.get(position);
    }

    public List<T> getData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        return mData;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (headerView != null) {
            count += 1;
        }
        if (footerView != null) {
            count += 1;
        }
        if (mData != null) {
            count += mData.size();
        }
        return count;
    }

    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!isHeader(position) && !isFooter(position)) {
            onBindViewHolderExtend(holder, position - getStart());
        }
    }

    protected abstract void onBindViewHolderExtend(VH holder, int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return getViewHolder(headerView);
        } else if (viewType == TYPE_FOOTER) {
            return getViewHolder(footerView);
        } else {
            View itemView = mInflater.inflate(getItemViewLayoutId(), parent, false);
            return getViewHolder(itemView);
        }
    }

    protected abstract int getItemViewLayoutId();

    @Override
    public final int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        } else {
            position = getStart() > 0 ? position - 1 : position;
            return getAdapterItemViewType(position);
        }
    }

    public int getAdapterItemViewType(int position) {
        return TYPE_NORMAL;
    }

    public int getStart() {
        return headerView == null ? 0 : 1;
    }

    public boolean isFooter(int position) {
        return footerView != null && position >= getItemCount() - 1;
    }

    public boolean isHeader(int position) {
        return getStart() > 0 && position == 0;
    }

    protected abstract VH getViewHolder(View view);

    public abstract class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
            if (itemView != headerView && itemView != footerView) {
                bindViewHolder(itemView);
            }
        }

        public abstract void bindViewHolder(View itemView);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(View view, int position);
    }

    protected OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }
    public class FooterView extends android.support.v7.widget.AppCompatTextView {

        public FooterView(Context context) {
            this(context, null);
        }

        public FooterView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        public void init(Context context) {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(50));
            setLayoutParams(params);
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            setGravity(Gravity.CENTER);
//        setText("加载中...");
        }
    }

}
