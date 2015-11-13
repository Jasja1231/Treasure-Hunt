package com.example.yaryna.hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * This fragment represent list of locations with details of specified HuntInscatnce.
 * Displays on the screen when user chooses hunt from all hunts list to diplay hunt details.
 */
public class ViewHuntsDetails extends Fragment {
    View view;
    /**Hunt we clicked on to view details (In case of guest)*/
    HuntInstance hunt;
    TextView huntRepresentNameField;

    public void  setCurrentHunt(HuntInstance hunt){
        this.hunt = hunt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_hunts_details, container, false);

        huntRepresentNameField = (TextView) view.findViewById(R.id.hunt_represent_name_field);
        String huntRepresentText = "You are on hunt " + hunt.getName() + " by creator " + hunt.getCreator() + " ,enjoy it!";
        huntRepresentNameField.setText(huntRepresentText);
        return view;
    }
}
