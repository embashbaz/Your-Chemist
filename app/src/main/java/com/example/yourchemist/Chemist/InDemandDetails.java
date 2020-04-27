package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yourchemist.R;

/**
 * A simple {@link Fragment} subclass.
 */
//This fragment will be implemented later, and will allow chemists to see more details about t
// he drugs that are needed and and the chemists that have them in the area
public class InDemandDetails extends Fragment {

    public InDemandDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_demand_details, container, false);
    }
}
