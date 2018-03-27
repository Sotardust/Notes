package com.dht.notes.testcode.ontouch.Recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dht.notes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/3/27.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyAdapter";


    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_recycle_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).btn.setText("按钮 " + list.get(position));
            ((ViewHolder) holder).btn1.setText("按钮1 " + list.get(position));
            ((ViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick() returned: btn" +position);
                }
            });
            ((ViewHolder) holder).btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick() returned: btn1"+position);
                }
            });
            ((ViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick() returned: linearLayout"+position );
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn)
        Button btn;
        @BindView(R.id.btn1)
        Button btn1;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
