package com.example.yaryna.hunt;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 15038588
 */
public class SettingsDialog extends DialogFragment {

    String username = Username.getInstance().username;
    View view;
    EditText editText;
    Button cancelButton, saveButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.dialog_settings, container, false);

        /**Edit text Field*/
        editText = (EditText) view.findViewById(R.id.edit_username_text);
        /**Set username edit text field default username(as text to show)*/
        editText.setText(Username.getInstance().getUsername());

        /**Cancel button*/
        cancelButton =(Button) view.findViewById(R.id.dialog_button_close);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on cancel button  click
                dismiss();
            }
        });

        /**Save Button*/
        saveButton = (Button)view.findViewById(R.id.dialog_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean nameChanged = true;
                String newUsername = editText.getText().toString();
                if(Username.getInstance().getUsername().equalsIgnoreCase(newUsername))
                    nameChanged = false;
                // Perform action on save button click
                if(Username.getInstance().IsValidUsername(newUsername)){
                    Username.getInstance().changeUsername(newUsername);
                    editText.setText(newUsername);
                    dismiss();

                if(nameChanged == true){
                    //just in case return new User to home screen of huntlist
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,new HuntList())
                            .commit();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Invalid username",Toast.LENGTH_LONG).show();
                }
            }


        });

        /*Widow without title bar on top ;) FINALLY STUPID TOOLBAR IS GONE,after hours of research*/
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
