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
        ArrayList<Float> values = new ArrayList<>();
        values.add(33f);
        values.add(37f);
        values.add(32f);
        values.add(31f);
        values.add(39f);
        values.add(22f);
        values.add(44f);
        values.add(35f);
        values.add(21f);

        ArrayList<Float> cValues = new ArrayList<>();
        cValues.add(35f);
        cValues.add(39f);
        cValues.add(35f);
        cValues.add(39f);
        cValues.add(44f);
        cValues.add(25f);
        cValues.add(47f);
        cValues.add(37f);
        cValues.add(25f);

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
