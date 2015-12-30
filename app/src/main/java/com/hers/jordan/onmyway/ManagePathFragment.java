package com.hers.jordan.onmyway;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hers.jordan.onmyway.model.Step;

import java.util.ArrayList;

public class ManagePathFragment extends Fragment {


    private static EditText editTextName;
    private static EditText editTextStart;
    private static EditText editTextPositionLong;
    private static EditText editTextPositionLat;

    private static ListView listViewSteps;
    private ArrayList<Step> listItems = new ArrayList<>();

    private static Button addButton;
    private static Button removeButton;
    private static Button createButton;

	public ManagePathFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_managepath, container, false);

        // init editText
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextStart = (EditText) rootView.findViewById(R.id.editTextStart);
        editTextPositionLong = (EditText) rootView.findViewById(R.id.editTextPositionLong);
        editTextPositionLat = (EditText) rootView.findViewById(R.id.editTextPositionLat);

        // init listView and click event
        listViewSteps = (ListView) rootView.findViewById(R.id.listViewSteps);

        // init button and button click listener
        addButton = (Button) rootView.findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listViewSteps.
            }
        });

        removeButton = (Button) rootView.findViewById(R.id.buttonRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeButton.setTextColor(Color.RED);
            }
        });

        createButton = (Button) rootView.findViewById(R.id.buttonCreate);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createButton.setTextColor(Color.RED);
            }
        });

        return rootView;
    }



}
