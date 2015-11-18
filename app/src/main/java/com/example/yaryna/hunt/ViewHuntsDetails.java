package com.example.yaryna.hunt;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * This fragment represent list of locations with details of specified HuntInscatnce.
 * Displays on the screen when user chooses hunt from all hunts list to diplay hunt details.
 */
public class ViewHuntsDetails extends Fragment implements Updatable{
    //TODO: Add private fields spec
    View view;
    /**Hunt we clicked on to view details,
     * gets assigned every time we click on a hunt in the list*/
    HuntInstance hunt;
    TextView huntRepresentNameField;
    Button playAndRegister;
    Button editButton;
    ListView listLocationsView;
    ArrayAdapter<Location> listAllLocationsAdapter;
    ArrayAdapter<String> listReachedStringsAdapter;

    ArrayList<String> reachedLocations  = new ArrayList<>(); //Locations name reached by user
    boolean reachedEndOfHunt = false;   // specify if user reached the end of the current hunt
    boolean userIsRegistered = false;   //Default- user not registered


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
        listLocationsView = (ListView) view.findViewById(R.id.list_locations);

        //Assign adapters for the listview
        listAllLocationsAdapter = new ArrayAdapter<Location>(getActivity(),
                android.R.layout.simple_list_item_1, this.hunt.getAllHuntLocations());
        listReachedStringsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, this.reachedLocations);

        //check If User Registered
        GetRegisteredUsersOnHuntRequest getRegisteredUsersOnHuntRequest = new GetRegisteredUsersOnHuntRequest(this/*fragment is to_update*/,hunt);
        getRegisteredUsersOnHuntRequest.execute();

        //Get all of the locations in the hunt if hunt does nothave them saved yet
        //if(this.hunt.getAllHuntLocations() == null || newLocationAdded == true)
        new GetAllHuntLocationsRequest(this,hunt).execute();
        GetReachedLocationsRequest getReachedLocationsRequest = new GetReachedLocationsRequest(this,this.hunt);
        getReachedLocationsRequest.execute();

        listAllLocationsAdapter.notifyDataSetChanged();
        listReachedStringsAdapter.notifyDataSetChanged();

        //handling listView display locations

        if(hunt.isMyHunt() == true){
            if(this.hunt.getAllHuntLocations().size() > 0){
                listLocationsView.setAdapter(listAllLocationsAdapter);
            }
                else if (this.hunt.getAllHuntLocations().size() > 0){
                TextView statusLocationSummary = (TextView) view.findViewById(R.id.status_location_summary);
                statusLocationSummary.setText("You didn't add any locations yet");
            }

        }
        else{
            if(this.reachedLocations.size() > 0){
                listLocationsView.setAdapter(listReachedStringsAdapter);
            }
            else if (this.reachedLocations.size() == 0 ){
                TextView statusLocationSummary = (TextView) view.findViewById(R.id.status_location_summary);
                statusLocationSummary.setText("No reached locations");
            }
        }



        /**Play and register on hunt button handling*/
        playAndRegister = (Button) view.findViewById(R.id.play_button);

        //set setOnClickListener - registed on resume game on click
        playAndRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIsRegistered == true){
                    Toast.makeText(getContext(),"You are already registered on this hunt,You can resume your game!",Toast.LENGTH_SHORT).show();
                    play();
                }
                else if(userIsRegistered == false){
                    registerUserOnHunt();//register user
                    play();
                }

            }
        });

        /**Edit button enabled only  for creator of the hunt*/
        editButton = (Button) view.findViewById(R.id.edit_button);
        //Hide button from non-creator
        if(hunt.isMyHunt()==false)
            editButton.setVisibility(View.GONE);
        return view;
    }


    /**Play method starts the game by opening next clue dialog or shows
     * notification if game if finished and user reached the end of hunt*/
    private void play(){
        if(reachedEndOfHunt== true ){
            Toast.makeText(getContext(),"There is no other available locations available yet...Wait for creator to add some more :)",Toast.LENGTH_LONG).show();
        }
        else if (reachedEndOfHunt== false){
            GetReachedLocationsRequest getReachedLocationsRequest = new GetReachedLocationsRequest(this,this.hunt);
            getReachedLocationsRequest.execute();
        }
    }

    private void registerUserOnHunt(){
        PostRegisterUserRequest postRegisterRequest = new PostRegisterUserRequest(prepareDataTosend(),this,this.hunt,Username.getInstance().getUsername());
        postRegisterRequest.execute();
    }

    /**Returs byte[] array of data to be send*/
    private byte[] prepareDataTosend(){
        String dataToSend = "huntname=" + this.hunt.getName()
                          + "&username="+Username.getInstance().getUsername();
        return dataToSend.getBytes(StandardCharsets.UTF_8);
    }

    private void updateButtonStatus(Button btn){
        if(userIsRegistered)
            btn.setText("Resume game");
        else/**Not registered user*/
            btn.setText("Register&Play");
    }

    public void updateAllHuntLocations(Object o){
        //setAllLocations of the hunt
        ArrayList<Location> l = (ArrayList<Location>) o;
        this.hunt.setAllHuntLocations(l);
    }
    @Override
    public void newResultsToUpdate(Object new_results) {
        ArrayList<String> result = (ArrayList<String>) new_results;
        for (String s : result) {
          if(Username.getInstance().getUsername().equalsIgnoreCase(s))
              userIsRegistered = true;
        }
        updateButtonStatus(playAndRegister);
    }

    public void updateReachedLocations(Object o){
            ArrayList<String> newReachedlocations = (ArrayList<String>) o;
            this.reachedLocations  = newReachedlocations;
            listReachedStringsAdapter.notifyDataSetChanged();


        //Check  that hunt is complete
        if(this.reachedLocations.size() == hunt.getAllHuntLocations().size()){
            reachedEndOfHunt = true;
            Toast.makeText(getContext(),"You have completed this hunt. There are no other available locations available yet...Wait for creator to add some more :)",Toast.LENGTH_LONG).show();
        }

        if(hunt.getAllHuntLocations().size() > 0){
            Location returnLocation = hunt.getAllHuntLocations().get(hunt.getAllHuntLocations().size()-1);
            for (Location loc : hunt.getAllHuntLocations()) {
                boolean found = false;
                for (String reached : reachedLocations) {
                    if (loc.getName().equals(reached)) {
                        found = true;
                    }
                }
                if (!found) {
                    returnLocation = loc;
                    break;
                }
            }
            showQuestionDialog(returnLocation);
        }
        else {
            Toast.makeText(getActivity(),"The creator of this hunt didn't add  locations", Toast.LENGTH_SHORT).show();
        }
    }


    /**Opens dialog window with current location */
    private void showQuestionDialog(Location loc){
        GameDialog gameDialog = new GameDialog();
        gameDialog.setArguments(makeBundle(loc));
        gameDialog.show(getActivity().getFragmentManager(), "");
    }

    private Bundle makeBundle(Location loc){
        Bundle args = new Bundle();
        args.putString("locationName", loc.getName());
        args.putString("locationDescription", loc.getDescription());
        args.putString("locationLatitude", loc.getLatitude());
        args.putString("locationLongitude", loc.getLongitude());
        args.putString("locationHunt", hunt.getName());
        args.putString("locationPosition", loc.getPosition());
        //args.putBoolean("isLastLocation", huntComplete);
        args.putString("locationQuestion", loc.getQuestion());
        args.putString("locationClue", loc.getClue());
        args.putString("locationAnswer", loc.getAnswer());
        return args;
    }

    /**Update after User registered on a Hunt*/
    public void UpdateAfterRegister(Object new_results) {
        String resultOfPost = (String) new_results;
        if(resultOfPost == null)
            userIsRegistered = false;
        else if (resultOfPost != null){
            String toastString = "User " + Username.getInstance().getUsername()+ " is succesfully registered on a hunt!";
            Toast.makeText(getContext(),toastString,Toast.LENGTH_SHORT).show();
            userIsRegistered = true;
        }
        updateButtonStatus(playAndRegister);
    }
}
