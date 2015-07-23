package com.shometeam.ao.shome.Screens.RoomsPart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.shometeam.ao.shome.GraphicViews.GraphicView;
import com.shometeam.ao.shome.GraphicViews.GraphicWidgetData;
import com.shometeam.ao.shome.R;

import java.util.ArrayList;

import Networking.Message;
import Networking.NetworkConnector;


public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.ViewHolder> {

    private ArrayList<GraphicWidgetData> mDataset;
    private Activity mActivity;

    String mSetValue="20";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public GraphicView mGraphiView;

        public ViewHolder(View v) {
            super(v);
            mGraphiView = (GraphicView) v.findViewById(R.id.gr_view);
        }
    }

    public RoomRecyclerAdapter(ArrayList<GraphicWidgetData> dataset,Activity activity) {
        mDataset = dataset;
        mActivity = activity;
    }

    @Override
    public RoomRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_rooms_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RoomRecyclerAdapter.ViewHolder holder, final int position) {
        TextView nameTextView = (TextView) holder.itemView.findViewById(R.id.graphic_name);
        nameTextView.setText(mDataset.get(position).mName);
        switch (mDataset.get(position).mColor){
            case "blue":
                nameTextView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.blinkBlinkBlueColor));
                break;
            case "red":
                nameTextView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.blinkBlinkRedColor));
                break;
            case "yellow":
                nameTextView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.blinkBlinkYellowColor));
                break;
            case "green":
                nameTextView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.blinkBlinkGreenColor));
                break;
            default:
                nameTextView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_drawer_primary_dark));
                break;
        }

        ArrayList<Float> defArray = new ArrayList<>();
        defArray.add(1f);
        defArray.add(2f);
        defArray.add(3f);
        defArray.add(4f);
        holder.mGraphiView = holder.mGraphiView.withViewType(mDataset.get(position).mType).withValues(defArray);
        holder.mGraphiView.setName("");
        holder.mGraphiView.setColor(mDataset.get(position).mColor);

        /*NetworkAssync nac;
        int max_value_of_graphic = 60;
        switch (position){
            case 0:
                nac = new NetworkAssync("192.168.1.100", 2100, 2000, holder.mGraphiView,
                        Message.createInfoMessage(Message.PARAMETER.TEMPERATURE));
                break;
            case 1:
                nac = new NetworkAssync("192.168.1.100", 2100, 2000, holder.mGraphiView,
                        Message.createInfoMessage(Message.PARAMETER.HUMIDITY));
                break;
            case 2:
                max_value_of_graphic = 1023;
                nac = new NetworkAssync("192.168.1.100", 2100, 2000, holder.mGraphiView,
                        Message.createInfoMessage(Message.PARAMETER.LUMINOSITY));
                holder.mGraphiView = holder.mGraphiView.withMaxValue(max_value_of_graphic);
                holder.mGraphiView.invalidate();
                break;
            case 3:
                max_value_of_graphic = 900;
                nac = new NetworkAssync("192.168.1.100", 2100, 2000, holder.mGraphiView,
                        Message.createInfoMessage(Message.PARAMETER.PRESSURE));
                holder.mGraphiView = holder.mGraphiView.withMaxValue(max_value_of_graphic);
                holder.mGraphiView.invalidate();
                break;
            default:
                nac = new NetworkAssync("192.168.1.100", 2100, 2000, holder.mGraphiView,
                        Message.createInfoMessage(Message.PARAMETER.ALL));
        }
        nac.execute();*/

        ImageButton fab = (ImageButton) holder.itemView.findViewById(R.id.fab_room);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
                View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_rooms, null);
                dialogBuilder.setTitle(mDataset.get(position).mName);

                String key = "";

                NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.rooms_dialog_number_picker);
                NumberPickerListener nl = new NumberPickerListener();
                numberPicker.setOnValueChangedListener(nl);
                switch (position) {
                    case 0: //temperature
                        key = mActivity.getResources().getString(R.string.settings_key_temperature);
                        numberPicker.setMinValue(10);
                        numberPicker.setMaxValue(30);
                        TextView measureText = (TextView) dialogView.findViewById(R.id.rooms_dialog_measure_text);
                        measureText.setText("°C");
                        break;
                    case 1://humidity
                        key = mActivity.getResources().getString(R.string.settings_key_humidity);
                        numberPicker.setMinValue(0);
                        numberPicker.setMaxValue(100);
                        break;
                    case 2://luminosity
                        key = mActivity.getResources().getString(R.string.settings_key_luminosity);
                        numberPicker.setMinValue(0);
                        numberPicker.setMaxValue(1200);
                        break;
                    case 3://pressure
                        break;
                }
                mSetValue = Integer.toString(numberPicker.getValue());//чтобы первое значение тоже учитывалось вне зависимости от скрола пикера

                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton(android.R.string.ok, new OkButtonListener(key));
                dialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка Отмена
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setCancelable(true);
                dialogBuilder.create().show();
            }
        });

        //holder.mGraphiView.invalidate();
    }


    class NumberPickerListener implements NumberPicker.OnValueChangeListener{
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mSetValue = ""+newVal;
        }
    }

    class OkButtonListener implements DialogInterface.OnClickListener{
        String mKey;

        OkButtonListener(String key){
            mKey = key;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity.getApplicationContext());
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(mKey, mSetValue);
            edit.commit();
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.rooms_toast)+mSetValue, Toast.LENGTH_SHORT).show();


            try{
                NetworkConnector nc = new NetworkConnector("192.168.1.100",2100,2000) {
                    @Override
                    public void handle(Object obj) {

                    }
                };
                nc.start();
                if(mKey.equals(mActivity.getResources().getString(R.string.settings_key_temperature))){
                    nc.send(Message.createSetMessage(Message.PARAMETER.TEMPERATURE,Float.valueOf(mSetValue)));
                } else if (mKey.equals(mActivity.getResources().getString(R.string.settings_key_humidity))){
                    nc.send(Message.createSetMessage(Message.PARAMETER.HUMIDITY,Float.valueOf(mSetValue)));
                } else if(mKey.equals(mActivity.getResources().getString(R.string.settings_key_luminosity))){
                    nc.send(Message.createSetMessage(Message.PARAMETER.LUMINOSITY,Float.valueOf(mSetValue)));
                }
                nc.close();
            } catch (Exception ex){
                ex.printStackTrace();
            }


            dialog.dismiss();
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
