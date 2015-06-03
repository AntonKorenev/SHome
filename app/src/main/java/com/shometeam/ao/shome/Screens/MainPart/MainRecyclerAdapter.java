package com.shometeam.ao.shome.Screens.MainPart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shometeam.ao.shome.GraphicViews.GraphicView;
import com.shometeam.ao.shome.GraphicViews.GraphicWidgetData;
import com.shometeam.ao.shome.R;

import java.util.ArrayList;

/**
 * Created by ao on 09.05.15.
 */
public class MainRecyclerAdapter  extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private ArrayList<GraphicWidgetData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public GraphicView mGraphiView;

        public ViewHolder(View v) {
            super(v);
            mGraphiView = (GraphicView) v.findViewById(R.id.gr_view);
        }
    }

    public MainRecyclerAdapter(ArrayList<GraphicWidgetData> dataset) {
        mDataset = dataset;
    }

    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_main_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainRecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<Double> values = new ArrayList<>();
        values.add(33d);
        values.add(37d);
        values.add(32d);
        values.add(31d);
        values.add(39d);
        values.add(22d);
        values.add(44d);
        values.add(35d);
        values.add(21d);

        ArrayList<Double> cValues = new ArrayList<>();
        cValues.add(35d);
        cValues.add(39d);
        cValues.add(35d);
        cValues.add(39d);
        cValues.add(44d);
        cValues.add(25d);
        cValues.add(47d);
        cValues.add(37d);
        cValues.add(25d);

        holder.mGraphiView = holder.mGraphiView
                .withViewType(mDataset.get(position).mType)
                .withValues(values)
                .withComparableValues(cValues);
        holder.mGraphiView.setName(mDataset.get(position).mName);
        holder.mGraphiView.setColor(mDataset.get(position).mColor);
        holder.mGraphiView.invalidate();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
