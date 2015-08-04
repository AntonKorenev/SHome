package com.shometeam.ao.shome.Screens.MainPart;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shometeam.ao.shome.CustomGuiElements.GraphicViews.GraphicWidgetData;
import com.shometeam.ao.shome.R;
import com.shometeam.ao.shome.Screens.SampleFragment;

import java.util.ArrayList;

public class MainFragment extends SampleFragment {
    EditText mTargetEdit;
    EditText mMinEdit;
    EditText mMaxEdit;
    View mDialogView;
    AlertDialog.Builder mDialogBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<GraphicWidgetData> myDataset = getDataSet();

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Activity c = getActivity();
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


        final Context appContext = this.getActivity().getApplicationContext();
        final Activity mainActivity = this.getActivity();



        ImageButton fab = (ImageButton) view.findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogView = mainActivity.getLayoutInflater().inflate(R.layout.dialog_main, null);
                mMaxEdit = (EditText) mDialogView.findViewById(R.id.main_dialog_max_edit);
                mMinEdit = (EditText) mDialogView.findViewById(R.id.main_dialog_min_edit);
                mTargetEdit = (EditText) mDialogView.findViewById(R.id.main_dialog_target_temperature_edit);

                mDialogBuilder = new AlertDialog.Builder(mainActivity);
                mDialogBuilder.setTitle(getResources().getString(R.string.settings_category_energy_usage_title));
                mDialogBuilder.setView(mDialogView);
                mDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
                        SharedPreferences.Editor edit = prefs.edit();
                        if (!mMinEdit.getText().toString().isEmpty()) {
                            edit.putString(
                                    getResources().getString(R.string.settings_key_energy_usage_min),
                                    mMinEdit.getText().toString()
                            );
                        }
                        if (!mMaxEdit.getText().toString().isEmpty()) {
                            edit.putString(
                                    getResources().getString(R.string.settings_key_energy_usage_max),
                                    mMaxEdit.getText().toString()
                            );
                        }
                        if (!mTargetEdit.getText().toString().isEmpty()) {
                            edit.putString(
                                    getResources().getString(R.string.settings_key_energy_usage_target_temperature),
                                    mTargetEdit.getText().toString()
                            );
                        }
                        edit.commit();
                        dialog.dismiss();
                    }
                });
                mDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка Отмена
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialogBuilder.setCancelable(true);
                mDialogBuilder.create();
                mDialogBuilder.show();
            }
        });

        return view;
    }

    private ArrayList<GraphicWidgetData> getDataSet() {
        ArrayList<GraphicWidgetData> mDataSet = new ArrayList<>();

        mDataSet.add(new GraphicWidgetData("blue",getResources().getString(R.string.main_histogram_title),"histogram"));
        mDataSet.add(new GraphicWidgetData("blue",getResources().getString(R.string.main_graphic_title),"linear"));

        return mDataSet;
    }

}