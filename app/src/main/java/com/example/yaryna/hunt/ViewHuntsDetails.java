package com.example.yaryna.hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * This fragment represent list of locations with details of specified HuntInscatnce.
 * Displays on the screen when user chooses hunt from all hunts list to diplay hunt details.
 */
public class ViewHuntsDetails extends Fragment implements Updatable{
    View view;
    /**Hunt we clicked on to view details,
     * gets assigned every time we click on a hunt in the list*/
    HuntInstance hunt;
    TextView huntRepresentNameField;
    Button playAndRegister;
    Button editButton;


    Boolean userIsRegistered = false; //Default- user not registered

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

        //check If User Registered
        GetRegisteredUsersOnHuntRequest getRegisteredUsersOnHuntRequest = new GetRegisteredUsersOnHuntRequest(this/*fragment is to_update*/,hunt);
        getRegisteredUsersOnHuntRequest.execute();

        /**Play and register on hunt button handling*/
        playAndRegister = (Button) view.findViewById(R.id.play_button);
        //set setOnClickListener - registed on resume game on click
        playAndRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(/**REGISTERED ALREADY*/ userIsRegistered){
                    playAndRegister.setText("Resume game");
                }
                else/**Not registered user*/{
                    playAndRegister.setText("Register&Play");
                }
            }
        });

        /**Edit button enabled only  for creator of the hunt*/
        editButton = (Button) view.findViewById(R.id.edit_button);
        //Hide button from non-creator
        if(hunt.isMyHunt()==false) editButton.setVisibility(View.GONE);



        return view;
    }


    @Override
    public void newResultsToUpdate(Object new_results) {
        ArrayList<String> result = (ArrayList<String>) new_results;
        HashSet hashSet = new HashSet(result);
        if(hashSet.contains(Username.getInstance().getUsername()))
            userIsRegistered = true;
        else
            userIsRegistered = false;
    }
}
