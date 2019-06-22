package com.liuleilei.macbook.mydemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuleilei.macbook.mydemo.R;
import com.liuleilei.macbook.mydemo.inter.RecyclerInterface;

import java.util.List;

/**
 * create by liu
 * on2019/6/9
 */
public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private RecyclerInterface recyclerInterface;

    public MainActivityAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setRecyclerInterface(RecyclerInterface recyclerInterface) {
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_activity, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemTextView.setText(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.item_text_view);
           itemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerInterface!=null){
                        recyclerInterface.onItemListener(getPosition());
                    }
                }
            });
        }
    }
}
