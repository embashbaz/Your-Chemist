package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.yourchemist.R;


public class MedecineDetails extends Fragment {

    EditText scientificEt, genericEt, manufacturerEt, priceEt, currencyEt, detailsEt;
    Spinner availabilitySp;
    Button saveBt;


    public MedecineDetails() {
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
        View view = inflater.inflate(R.layout.fragment_medecine_details, container, false);
        scientificEt = view.findViewById(R.id.scientific_name_et);
        genericEt = view.findViewById(R.id.generic_name_et);
        manufacturerEt = view.findViewById(R.id.made_in_et);
        priceEt = view.findViewById(R.id.price_et);
        currencyEt = view.findViewById(R.id.currency_et);
        detailsEt = view.findViewById(R.id.drug_detail_et);
        saveBt = view.findViewById(R.id.save_medecine);

        return view;
    }
}
