package com.example.yourchemist.Chemist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

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
    private Bundle bundle;
    private String medId;
    private NavController navController;


    public MedecineDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((ChemistActivity)getActivity()).setActionBarTitle("Medicine Detail");
        View view = inflater.inflate(R.layout.fragment_medecine_details, container, false);
        scientificEt = view.findViewById(R.id.scientific_name_et);
        genericEt = view.findViewById(R.id.generic_name_et);
        manufacturerEt = view.findViewById(R.id.made_in_et);
        priceEt = view.findViewById(R.id.price_et);
        currencyEt = view.findViewById(R.id.currency_et);
        detailsEt = view.findViewById(R.id.drug_detail_et);
        availabilitySp = view.findViewById(R.id.availability_spinner);
        saveBt = view.findViewById(R.id.save_medecine);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        bundle = this.getArguments();
        if(bundle == null){
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getChemistInfo();
                }
            });
        }else {
            setDataView();
            saveBt.setText(R.string.update);
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateMedicine();
                }
            });

        }


    }


    private void getChemistInfo() {
        DocumentReference docRef = db.collection("pharmacies").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Chemist mChemist = documentSnapshot.toObject(Chemist.class);
                myChemistInfo(mChemist);
                setDataDb();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                        "your internet connection ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void myChemistInfo(Chemist chemist){
        mChemist = chemist;
    }

    private void getData(){

        if((!isEmpty(scientificEt.getText().toString()) || !isEmpty(scientificEt.getText().toString())) && !isEmpty(manufacturerEt.getText().toString()) &&
                !isEmpty(priceEt.getText().toString()) && !isEmpty(currencyEt.getText().toString()) && !isEmpty(availabilitySp.getSelectedItem().toString())
                ){
        scientificName = scientificEt.getText().toString().trim().trim().toLowerCase();
        genericName = genericEt.getText().toString().trim().toLowerCase();
        manufurerCountry = manufacturerEt.getText().toString().trim();
        price = Double.valueOf(priceEt.getText().toString().trim());
        currency = currencyEt.getText().toString().trim().toLowerCase();
        detail = detailsEt.getText().toString().trim().toLowerCase();
        availability = availabilitySp.getSelectedItem().toString();
        }else {
            Toast.makeText(getActivity(), "Please fill all the field, details about the drug are optional", Toast.LENGTH_LONG).show();
        }

    }


    private void setDataDb(){

        getData();
        if((!isEmpty(scientificName) || !isEmpty(genericName)) && !isEmpty(manufurerCountry) &&
                !isEmpty(priceEt.getText().toString()) && !isEmpty(currency) && !isEmpty(availability)
        && !scientificName.equals(genericName)){

            Medecine medecine = new Medecine(mChemist, scientificName,genericName,manufurerCountry,
                    currency, detail, price, availability);
            db.collection("Medicine").add(medecine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getActivity(), "Added this drug to your database", Toast.LENGTH_SHORT).show();
                    clearField();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Couldn't add this this item to the db, " +
                            "please try again or check your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Make sure that the scientific " +
                    "name and generic name are not the same and you can only put one if you are not sure about one the name", Toast.LENGTH_LONG).show();
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
    private void setDataView() {
        scientificEt.setText(bundle.getString("sName", ""));
        genericEt.setText(bundle.getString("gName"));
        manufacturerEt.setText(bundle.getString("countryMade"));
        priceEt.setText(bundle.getDouble("price")+"");
        currencyEt.setText(bundle.getString("currency"));
        detailsEt.setText(bundle.getString("detailMed"));

        String[] myvalue = getResources().getStringArray(R.array.availability_array);
        String gottenValue = bundle.getString("availability");

            for (int x = 0;x< myvalue.length;x++){
                if(gottenValue.equals(myvalue[x])){
                    availabilitySp.setSelection(x);
                }
            }




    }
    private void UpdateMedicine() {
        getData();
        medId = bundle.getString("id");
        if((!isEmpty(scientificName) || !isEmpty(genericName)) && !isEmpty(manufurerCountry) &&
                !isEmpty(priceEt.getText().toString()) && !isEmpty(currency) && !isEmpty(availability)
        && !scientificName.equals(genericName)) {
            db.collection("Medicine").document(medId)
                    .update(
                            "countryMade", manufurerCountry,
                            "currency",currency,
                            "detailsMed",detail,
                            "genericName", genericName,
                            "price", price,
                            "scientificName", scientificName,
                            "availability", availability

                            );
            navController.navigateUp();
           // navController.popBackStack();
            Toast.makeText(getContext(), "Medicine Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Make sure that the scientific " +
                    "name and generic name are not the same and you can only put one if you are not sure about one the name  ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.medicine_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_medicine:
                deleteDoc();



        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteDoc(){
        medId = bundle.getString("id");
        db.collection("Medicine").document(medId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        navController.navigateUp();
                       // navController.popBackStack();
                        Toast.makeText(getActivity(), "Medicine deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
