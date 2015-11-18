package com.example.yaryna.hunt;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by 15038588
 */
public class GameDialog extends DialogFragment {

    View view;

    //GUI elem
    TextView nameText;
    TextView descriptionText;
    TextView positionText;
    TextView longLatitText ;
    TextView questionText;
    TextView clueText;
    EditText answerEditText;
    Button submitAnswerButton;

    String answer;
    String userAnswer;
    String parenthuntName;
    String locationName;

    GameDialog thisInstance;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_question, container, false);

        //Get user interface components
         nameText = (TextView) view.findViewById(R.id.name_text);
         descriptionText = (TextView) view.findViewById(R.id.description_text);
         positionText = (TextView) view.findViewById(R.id.position_text);
         longLatitText = (TextView) view.findViewById(R.id.long_latit_text);
         questionText = (TextView) view.findViewById(R.id.question_text);
         clueText= (TextView) view.findViewById(R.id.clue_text);
         answerEditText = (EditText) view.findViewById(R.id.answer_edit_text);
         submitAnswerButton = (Button) view.findViewById(R.id.submit_answer_button);

        final Bundle args = getArguments();
        String nameTextString = args.getString("locationName");
        nameText.setText(nameTextString);

        String description = args.getString("locationDescription");
        descriptionText.setText(description);

        String position = "Position " + args.getString("locationPosition")+
                " in "+args.getString("locationHunt");
        positionText.setText(position);

        final String location = "Latitude: "+args.getString("locationLatitude") + "\nLongitude: "+args.getString("locationLongitude");
        longLatitText.setText(location);


        String nextQuestion =  args.getString("locationQuestion");
        questionText.setText(nextQuestion);

        String nextClue = args.getString("locationClue");
        clueText.setText(nextClue);

        answer = args.getString("locationAnswer");

        parenthuntName  = args.getString("locationHunt");
        thisInstance =this;
        locationName = args.getString("locationName");

        /*Widow without title bar on top ;) FINALLY STUPID TOOLBAR IS GONE,after hours of research*/
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = answerEditText.getText().toString();
                if (userAnswer.equalsIgnoreCase(answer)) {
                    //correct answer
                    PostSubmitReachLocationRequest postSubmitReachLocationRequest = new PostSubmitReachLocationRequest(thisInstance,parenthuntName,locationName);
                    postSubmitReachLocationRequest.execute();
                } else {
                    answerEditText.setText("");
                    Toast.makeText(getActivity(), "Wrong answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void postRequestComplete(Object o){
        //Updating after post request that user reached specified location
        boolean success = (boolean) o;
        if(success == true)
            Toast.makeText(getActivity(), "User reached location", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Error with post reaching location", Toast.LENGTH_SHORT).show();
    }
}
