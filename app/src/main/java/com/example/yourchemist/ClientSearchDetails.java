package com.example.yourchemist;

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

    TextView scientificTxt, genericTxt, manufacturerTxt, priceTxt, availabilityTxt, detailsTxt;
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
        detailsTxt = view.findViewById(R.id.drug_detail_txt);
        db = FirebaseFirestore.getInstance();
        getData();

        return view;
    }

    private void getData(){
        DocumentReference docRef = db.collection("Medicine").document(medId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Medecine med = documentSnapshot.toObject(Medecine.class);
                scientificTxt.setText(med.getScientificName());
                genericTxt.setText(med.getGenericName());
                manufacturerTxt.setText(med.getCountryMade());
                priceTxt.setText(med.getPrice()+"");
                availabilityTxt.setText("");
                detailsTxt.setText(med.getDetailsMed());

            }
        });

    }
}
