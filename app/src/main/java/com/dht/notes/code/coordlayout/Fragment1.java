package com.dht.notes.code.coordlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dht.notes.R;
import com.dht.notes.code.adapter.MainAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_1, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        String page = getArguments().getString("key");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            list.add(page + " 数据 = " + i);
        }
        MainAdapter mainAdapter = new MainAdapter(list);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}
