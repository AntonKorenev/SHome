package com.shometeam.ao.shome.Screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shometeam.ao.shome.R;

/**
 * Created by ao on 09.05.15.
 */
public class SampleFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    public SampleFragment() {
        // Required empty public constructor
    }

    public static SampleFragment newInstance(String title) {
        SampleFragment f = new SampleFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
