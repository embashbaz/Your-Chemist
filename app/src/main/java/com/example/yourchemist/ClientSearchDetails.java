package com.example.yourchemist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class ClientSearchDetails extends Fragment {

    TextView scientificTxt, genericTxt, manufacturerTxt, priceTxt, availabilityTxt, detailsTxt;


    public ClientSearchDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_search_details, container, false);

        scientificTxt = view.findViewById(R.id.scientific_name_txt);
        genericTxt = view.findViewById(R.id.generic_name_txt);
        manufacturerTxt = view.findViewById(R.id.made_in_txt);
        priceTxt = view.findViewById(R.id.price_txt);
        availabilityTxt = view.findViewById(R.id.availability_txt);
        detailsTxt = view.findViewById(R.id.drug_detail_txt);

        return view;
    }
}
