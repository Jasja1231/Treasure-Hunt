package com.example.yaryna.hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

/**
 * This is fragment to represent list of all hunts
 */

public class HuntList extends Fragment implements Updatable {

    View view;

    /**
     * ArrayList for storing all of the hunts
     */
    private ArrayList<HuntInstance> hunts = new ArrayList<>();
    /**
     * ArrayList adapter
     */
    private ArrayAdapter<HuntInstance> huntsAdapter;

    /**Constructor*/
    public HuntList(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hunt_list, container, false);


        /**Get ListView for displaying*/
        ListView huntsList = (ListView) view.findViewById(R.id.hunts_view_list);
        huntsAdapter = new ArrayAdapter<HuntInstance>(getActivity(), android.R.layout.simple_list_item_1, hunts);
        huntsList.setAdapter(huntsAdapter);

        //hunts.add(new HuntInstance("Dummy","www.dummy.org"));
        //

        GetHuntsRequest request = new GetHuntsRequest(this);
        request.execute();
        huntsAdapter.notifyDataSetChanged();

        /**Handle view details on item (hunt item) click */
        huntsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**Creating new fragment to view information about the event*/
                ViewHuntsDetails viewHuntsDetails = new ViewHuntsDetails();
                /**Set current hunt we are viewving*/
                viewHuntsDetails.setCurrentHunt(hunts.get(position));
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, viewHuntsDetails)
                        .commit();
                String output =  Integer.toString(position) + " " + hunts.get(position).toString();
                Toast.makeText(getContext(),output , Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    @Override
    public void newResultsToUpdate(Object new_results) {
        ArrayList<HuntInstance> result = (ArrayList<HuntInstance>) new_results;
        hunts.clear();
        hunts.addAll(result);
        huntsAdapter.notifyDataSetChanged();
    }
}
