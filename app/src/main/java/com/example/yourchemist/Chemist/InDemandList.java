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
public class InDemandList extends Fragment {

    public InDemandList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_demand_list, container, false);
    }
}
