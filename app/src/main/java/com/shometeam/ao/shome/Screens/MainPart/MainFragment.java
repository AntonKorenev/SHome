package com.shometeam.ao.shome.Screens.MainPart;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shometeam.ao.shome.GraphicViews.GraphicWidgetData;
import com.shometeam.ao.shome.R;
import com.shometeam.ao.shome.Screens.SampleFragment;

import java.util.ArrayList;

public class MainFragment extends SampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<GraphicWidgetData> myDataset = getDataSet();

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final MainRecyclerAdapter adapter = new MainRecyclerAdapter(getDataSet());
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        return view;
    }

    private ArrayList<GraphicWidgetData> getDataSet() {
        ArrayList<GraphicWidgetData> mDataSet = new ArrayList<>();

        mDataSet.add(new GraphicWidgetData("blue",getResources().getString(R.string.main_histogram_title),"histogram"));
        mDataSet.add(new GraphicWidgetData("blue",getResources().getString(R.string.main_graphic_title),"linear"));

        return mDataSet;
    }

}