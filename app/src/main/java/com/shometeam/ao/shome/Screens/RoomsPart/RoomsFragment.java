package com.shometeam.ao.shome.Screens.RoomsPart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shometeam.ao.shome.GraphicViews.GraphicWidgetData;
import com.shometeam.ao.shome.R;

import java.util.ArrayList;

/**
 * Created by ao on 20.04.15.
 */
public class RoomsFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int mRoomNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<GraphicWidgetData> myDataset = getDataSet();

        final View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_rooms);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final RoomRecyclerAdapter adapter = new RoomRecyclerAdapter(getDataSet(), getActivity());

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
        mDataSet.add(new GraphicWidgetData("def",getResources().getString(R.string.rooms_temperature_title),"linear"));
        mDataSet.add(new GraphicWidgetData("def",getResources().getString(R.string.rooms_humidity_title),"linear"));//добавть еще одно поле для значений
        mDataSet.add(new GraphicWidgetData("def",getResources().getString(R.string.rooms_luminosity_title),"linear"));
        mDataSet.add(new GraphicWidgetData("def",getResources().getString(R.string.rooms_pressure_title),"linear"));
        /*mDataSet.add(new GraphicWidgetData("red",getResources().getString(R.string.rooms_temperature_title),"linear"));
        mDataSet.add(new GraphicWidgetData("blue",getResources().getString(R.string.rooms_humidity_title),"linear"));//добавть еще одно поле для значений
        mDataSet.add(new GraphicWidgetData("yellow",getResources().getString(R.string.rooms_luminosity_title),"linear"));
        mDataSet.add(new GraphicWidgetData("green",getResources().getString(R.string.rooms_pressure_title),"linear"));*/
        return mDataSet;
    }


    public static RoomsFragment newInstance(int page) {//создает 4ре графика для комнаты
        RoomsFragment roomsFragment = new RoomsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        roomsFragment.setArguments(arguments);
        return roomsFragment;
    }

}
