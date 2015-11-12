package com.example.yaryna.hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This fragment represent list of locations with details of specified HuntInscatnce.
 * Displays on the screen when user chooses hunt from all hunts list to diplay hunt details.
 */
public class ViewHuntsDetails extends Fragment {
    View view;
    /**Hunt we clicked on to view details (In case of guest)*/
    HuntInstance hunt;
    public String FRAGMENT_BUNDLE_KEY = "com.example.yaryna.hunt.viewhuntdetails.FRAGMENT_BUNDLE_KEY";

    public void  setCurrentHunt(HuntInstance hunt){
        this.hunt = hunt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_hunts_details, container, false);
        return view;
    }
}
