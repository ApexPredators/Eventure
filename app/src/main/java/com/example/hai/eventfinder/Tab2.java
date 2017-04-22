package com.example.hai.eventfinder;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    Logger log = Logger.getAnonymousLogger();

    Button timeButton;
    Button dateButton;
    Button submitButton;
    FloatingActionButton cameraButton;
    Intent intent;

    //Get all the edit text inputs
    EditText eventName, eventDescription, eventLocation, eventURL, eventDate, eventTime;

    //Store the above inputs as strings
    //This is going to be the array list that holds all the information of the event
    ArrayList<String> eventDetails = new ArrayList<>();
    Context c;
    //This is references itself so that we do not have to make
    //the methods inside the mainActivity
    Tab2 myself = this;

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        c = view.getContext();
        eventName = (EditText) view.findViewById(R.id.editEventName);
        eventDescription = (EditText) view.findViewById(R.id.eventDescription);
        eventLocation = (EditText) view.findViewById(R.id.eventLocation);
        eventURL = (EditText) view.findViewById(R.id.EventURL);
        eventDate= (EditText) view.findViewById(R.id.timeEdit);
        eventTime = (EditText) view.findViewById(R.id.dateEdit);

        cameraButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        intent = new Intent(view.getContext(), OcrCameraActivity.class);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Camera Button", "Camera activity started");
                startActivity(intent);
            }
        });
        return view;
    }

    //this method is because of the life cycle,
    //to help avoid button referencing a null object
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        timeButton = (Button) getView().findViewById(R.id.pickTime);
        dateButton = (Button) getView().findViewById(R.id.pickDate);
        submitButton = (Button) getView().findViewById(R.id.submit);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myself.showDatePickerDialog(view);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myself.showTimePickerDialog(view);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the added input as a string to the ArrayList
                eventDetails.add(eventName.getText().toString());
                eventDetails.add(eventDescription.getText().toString());
                eventDetails.add(eventLocation.getText().toString());
                eventDetails.add(eventURL.getText().toString());
                eventDetails.add(eventTime.getText().toString());
                eventDetails.add(eventDate.getText().toString());

                //runs the thread to insert the event into the database
                DynamoThread thread = new DynamoThread(eventDetails, c);
                thread.runDynamo();

                Toast.makeText(getContext(), "Submit complete", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static Tab2 newInstance(){
        Tab2 tab2 = new Tab2();
        return tab2;
    }

    //test stub
    public void setNumber(int num){
        log.info("Number is: " + num);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getActivity().getFragmentManager() , "timePicker");

    }


    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


}
