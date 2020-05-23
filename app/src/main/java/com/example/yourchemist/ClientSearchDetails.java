package com.example.yourchemist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yourchemist.AdapterAndModel.Medecine;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ClientSearchDetails extends Fragment {

    TextView scientificTxt, genericTxt, manufacturerTxt, priceTxt, availabilityTxt, detailsTxt,
            chemistName, chemistPhone, chemist_email, chemist_adress, chemistDetail, googleLoc;
    String medId;
    private FirebaseFirestore db;

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
        ((MainActivity)getActivity()).setActionBarTitle("Medicine detail");
        View view = inflater.inflate(R.layout.fragment_client_search_details, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            medId = bundle.getString("id", "");
            }

        scientificTxt = view.findViewById(R.id.scientific_name_txt);
        genericTxt = view.findViewById(R.id.generic_name_txt);
        manufacturerTxt = view.findViewById(R.id.made_in_txt);
        priceTxt = view.findViewById(R.id.price_txt);
        availabilityTxt = view.findViewById(R.id.availability_txt);
        chemistName = view.findViewById(R.id.chemist_name_detail);
        chemist_adress = view.findViewById(R.id.adress_detail);
        chemist_email = view.findViewById(R.id.email_detail);
        chemistDetail = view.findViewById(R.id.pharmacy_detail);
        chemistPhone = view.findViewById(R.id.phone_detail);
        detailsTxt = view.findViewById(R.id.drug_detail_txt);
        googleLoc = view.findViewById(R.id.google_location_txt);
        db = FirebaseFirestore.getInstance();
        getData();

        return view;
    }

    private void getData(){
        DocumentReference docRef = db.collection("Medicine").document(medId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final Medecine med = documentSnapshot.toObject(Medecine.class);
                scientificTxt.setText(med.getScientificName());
                genericTxt.setText(med.getGenericName());
                manufacturerTxt.setText("Made in "+med.getCountryMade());
                priceTxt.setText(med.getPrice()+" "+med.getCurrency());
                availabilityTxt.setText(med.getAvailability());
                detailsTxt.setText(med.getDetailsMed());
                chemistName.setText(med.getName());
                chemist_adress.setText(med.getAdress()+", "+med.getArea()+", "+med.getTown());
                chemist_email.setText(med.getEmail());
                chemistDetail.setText(med.getDetails());
                chemistPhone.setText(med.getPhone());
                googleLoc.setText(med.getAreaCode()+" (Click here to open location in google map)");
                googleLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Uri gmmIntentUri = Uri.parse("google.navigation:q=" +med.getAdress()+"+"+med.getArea()+","+med.getTown()+"+"+med.getCountry());
                        Uri gmmIntentUri = Uri.parse("http://plus.codes/"+med.getAreaCode());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

            }
        });

    }
}
