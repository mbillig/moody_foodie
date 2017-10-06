package com.example.marie.moodyfoodie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LogFragment extends Fragment implements View.OnClickListener{

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.log_fragment, container, false);

        Button foodButton = (Button) view.findViewById(R.id.addFoodButton);
        Button activityButton = (Button) view.findViewById(R.id.addActivityButton);
        Button moodButton = (Button) view.findViewById(R.id.addMoodButton);
        Button notesButton = (Button) view.findViewById(R.id.addNoteButton);
        foodButton.setOnClickListener(this);
        activityButton.setOnClickListener(this);
        moodButton.setOnClickListener(this);
        notesButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.addFoodButton:
                System.out.println("Food");
                ((MainActivity)getActivity()).pushFragment(new FoodFragment());
                break;
            case R.id.addActivityButton:
                System.out.println("Activity");
                ((MainActivity)getActivity()).pushFragment(new ActivityFragment());
                break;
            case R.id.addMoodButton:
                System.out.println("Mood");
                ((MainActivity)getActivity()).pushFragment(new MoodFragment());
                break;
            case R.id.addNoteButton:
                System.out.println("Notes");
                ((MainActivity)getActivity()).pushFragment(new NotesFragment());
                //AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Enter Note");
                break;
        }
    }
}
