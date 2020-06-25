package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.Medecine;
import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
//This fragment will be implemented later, and will allow chemists to see more details about t
// he drugs that are needed and and the chemists that have them in the area
public class InDemandDetails extends Fragment {

    TextView drugDetails, storeDetail;
    String country, town, drugName;
    private FirebaseFirestore db;

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
        db = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drugName = bundle.getString("drug", "");
            country = bundle.getString("country", "");
            town = bundle.getString("town", "");
            drugDetails.setText("The drug is "+drugName+"\nfrom "+ country+"/"+
                    town+"\nand requested by "+ bundle.getInt("request", 0)+" people");
        }
        getData();
        return view;
    }
    public void getData() {

        Task task1 = db.collection("Medicine")
                .whereEqualTo("scientificName", drugName)
                .whereEqualTo("country", country)
                .whereEqualTo("town", town)
                .get();
        Task task2 = db.collection("Medicine")
                .whereEqualTo("genericName", drugName)
                .whereEqualTo("country", country)
                .whereEqualTo("town", town)
                .get();

        Task<List<QuerySnapshot>> allData = Tasks.whenAllSuccess(task1, task2);
        allData.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {

                String data ="";
                if (task.isSuccessful()) {

                    for (QuerySnapshot query : task.getResult()) {
                        for (QueryDocumentSnapshot document : query) {
                            Medecine medecine = document.toObject(Medecine.class);
                            if(medecine != null){
                                data = data + medecine.getName() +": "+ medecine.getAvailability()+"\n";
                            }

                        }
                    }
                    if (data.isEmpty())
                        storeDetail.setText("No one has this drug yet");
                    else
                    storeDetail.setText(data);
                }
                else {
                    storeDetail.setText("No one has this drug yet");
                }
            }
        });


    }
}
