package com.shometeam.ao.shome.Screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shometeam.ao.shome.R;

/**
 * Created by ao on 20.04.15.
 */
public class HelpFragment  extends SampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}
