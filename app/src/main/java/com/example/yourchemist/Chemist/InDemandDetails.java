package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yourchemist.R;

/**
 * A simple {@link Fragment} subclass.
 */
//This fragment will be implemented later, and will allow chemists to see more details about t
// he drugs that are needed and and the chemists that have them in the area
public class InDemandDetails extends Fragment {

    TextView drugDetails, storeDetail;

    public InDemandDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_demand_details, container, false);
        drugDetails = view.findViewById(R.id.indemand_drug_details);
        storeDetail = view.findViewById(R.id.indemand_chemist_details);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drugDetails.setText("The drug is "+bundle.getString("drug", "")+"\nfrom "+
                    bundle.getString("country", "")+"/"+
                    bundle.getString("town", "")+"\nand requested by "+
                    bundle.getInt("request", 0)+" people");
        }

        return view;
    }
}
