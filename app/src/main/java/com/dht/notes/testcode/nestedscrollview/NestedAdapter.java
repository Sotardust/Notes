package com.dht.notes.testcode.nestedscrollview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dht.notes.R;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter {

    private static final String TAG = "dht";

    private List<String> list;

    public NestedAdapter (List<String> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
//        Log.d(TAG, "onCreateViewHolder() c i = [" + i + "]");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_nested, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        Log.d(TAG, "onBindViewHolder()  i = [" + i + "]");
        if (viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).content.setText(list.get(i));
        }

    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView content;

        private ViewHolder (@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.item_content);
        }
    }
}
