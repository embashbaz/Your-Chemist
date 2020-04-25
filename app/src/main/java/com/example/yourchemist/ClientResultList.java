package com.example.yourchemist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.ChemistAdapter;
import com.example.yourchemist.AdapterAndModel.Indemand;
import com.example.yourchemist.AdapterAndModel.Medecine;
import com.example.yourchemist.AdapterAndModel.UserResultAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientResultList extends Fragment {

    private RecyclerView recyclerView;
    private UserResultAdapter userResultAdapter;
    private TextView noResult;
    private ArrayList<Medecine> medecinesList = new ArrayList<>();
    private String drugName, townName, areaName, countryName;
    private FirebaseFirestore db;

    public ClientResultList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drugName = bundle.getString("drug", "");
            townName = bundle.getString("town", "");
            areaName = bundle.getString("area", "");
            countryName = bundle.getString("country","");
        }
        View view = inflater.inflate(R.layout.fragment_client_result_list, container, false);
        recyclerView = view.findViewById(R.id.client_result_list);
        noResult = view.findViewById(R.id.no_data_client_results);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        noResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToInDemand();
            }

        });
        getData();
    }

    public void getData(){

        Task task1 = db.collection("Medicine")
                .whereEqualTo("scientificName", drugName)
                .whereEqualTo("country", countryName)
                .whereEqualTo("town", townName)
                .get();
        Task task2 = db.collection("Medicine")
                .whereEqualTo("genericName", drugName)
                .whereEqualTo("country", countryName)
                .whereEqualTo("town", townName)
                .get();

        Task<List<QuerySnapshot>> allData = Tasks.whenAllSuccess(task1, task2);
        allData.addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
            @Override
            public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                if(task.isSuccessful()){
                    medecinesList.clear();
                    for(QuerySnapshot query : task.getResult()){
                        for (QueryDocumentSnapshot document: query){
                            Medecine medecine = document.toObject(Medecine.class);
                            medecine.setMedUid(document.getId());
                            Log.d("this ", document.getId() + " => " + document.getData());
                            medecinesList.add(medecine);
                        }
                    }
                    if(medecinesList.size() == 0){
                        noResult.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }else {
                        userResultAdapter = new UserResultAdapter(medecinesList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(userResultAdapter);
                    }
                }
            }
        });


    }
    private void addDataToInDemand() {


        db.collection("inDemand")
                .whereEqualTo("drugName", drugName)
                .whereEqualTo("countryName", countryName)
                .whereEqualTo("townName", townName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Indemand indemand=null;

                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                indemand = document.toObject(Indemand.class);
                                indemand.setDocId(document.getId());
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            if(indemand == null){
                                indemand = new Indemand(drugName,townName,countryName, 1);
                                db.collection("inDemand").add(indemand);
                            }else {
                                db.collection("inDemand").document(indemand.getDocId())
                                        .update(
                                                "numberRequest", indemand.getNumberRequest()+1
                                        );
                            }
                        }else{
                            indemand = new Indemand(drugName,townName,countryName, 1);
                            db.collection("inDemand").add(indemand);
                        }
                    }
                });
    }
}
