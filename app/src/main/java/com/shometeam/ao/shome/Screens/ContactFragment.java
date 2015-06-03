package com.shometeam.ao.shome.Screens;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shometeam.ao.shome.R;

/**
 * Created by ao on 20.04.15.
 */
public class ContactFragment extends SampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contactView = inflater.inflate(R.layout.fragment_contact, container, false);

        // Getting previous text from shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(inflater.getContext());
        final SharedPreferences.Editor edit = prefs.edit();
        final TextView message = (TextView) contactView.findViewById(R.id.message_devs);
        final String key = getResources().getString(R.string.settings_key_message_to_devs);
        String messageText = prefs.getString(
                key, getResources().getString(R.string.contact_message_value)
        );
        message.setText(messageText);

        //button listener, which saves text to shared preferences and TODO: send message via email
        ImageButton sendButton = (ImageButton) contactView.findViewById(R.id.fab_contact);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString(key, message.getText().toString());
                edit.commit();
            }
        });

        return contactView;
    }
}
