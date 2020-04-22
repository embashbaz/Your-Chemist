package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.Chemist;
import com.example.yourchemist.AdapterAndModel.Medecine;
import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.text.TextUtils.isEmpty;


public class MedecineDetails extends Fragment {

    private EditText scientificEt, genericEt, manufacturerEt, priceEt, currencyEt, detailsEt;
    private Spinner availabilitySp;
    private String scientificName, genericName, manufurerCountry, currency, detail, availability;
    private double price;
    private Button saveBt;
    private Chemist mChemist;
    private String uid;
    private FirebaseFirestore db;
    private String TAG = "medecine details";


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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChemistInfo();
            }
        });
    }




    private void getChemistInfo() {
        DocumentReference docRef = db.collection("pharmacies").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Chemist mChemist = documentSnapshot.toObject(Chemist.class);
                myChemistInfo(mChemist);
                setData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void myChemistInfo(Chemist chemist){
        mChemist = chemist;
    }

    private void getData(){

        scientificName = scientificEt.getText().toString();
        genericName = genericEt.getText().toString();
        manufurerCountry = manufacturerEt.getText().toString();
        price = Double.valueOf(priceEt.getText().toString());
        currency = currencyEt.getText().toString();
        detail = detailsEt.getText().toString();

    }


    private void setData(){

        getData();
        if(!isEmpty(scientificName) && !isEmpty(genericName) && !isEmpty(manufurerCountry) &&
                !isEmpty(priceEt.getText().toString()) && !isEmpty(currency)){
            //getData();
            Medecine medecine = new Medecine(mChemist, scientificName,genericName,manufurerCountry,
                    currency, detail, price);
            db.collection("Medicine").add(medecine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getActivity(), "added this drug to your database", Toast.LENGTH_SHORT).show();
                    clearField();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "couldn't add this this item to the db, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "please fill all the field, details about the drug are optional ", Toast.LENGTH_LONG).show();
        }


    }

    private void clearField(){
        scientificEt.setText("");
        genericEt.setText("");
        manufacturerEt.setText("");
        priceEt.setText("");
        currencyEt.setText("");
        detailsEt.setText("");
    }

}
