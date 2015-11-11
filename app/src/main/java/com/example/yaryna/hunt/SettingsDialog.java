package com.example.yaryna.hunt;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Yaryna on 05/11/2015.
 */
public class SettingsDialog extends DialogFragment {

    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_settings, container, false);

        /*Widow without title bar on top ;) FINALLY STUPID TOOLBAR IS GONE,after hours of research*/
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
