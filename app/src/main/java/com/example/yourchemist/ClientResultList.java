package com.example.yourchemist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


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
        ((MainActivity)getActivity()).setActionBarTitle("Drug List");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drugName = bundle.getString("drug", "");
            townName = bundle.getString("town", "");
            //areaName = bundle.getString("area", "");
            countryName = bundle.getString("country", "");
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

    public void getData() {

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
                if (task.isSuccessful()) {
                    medecinesList.clear();
                    for (QuerySnapshot query : task.getResult()) {
                        for (QueryDocumentSnapshot document : query) {
                            Medecine medecine = document.toObject(Medecine.class);
                            medecine.setMedUid(document.getId());
                            Log.d("this ", document.getId() + " => " + document.getData());
                            medecinesList.add(medecine);
                        }
                    }
                    if (medecinesList.size() == 0) {
                        noResult.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    } else {
                        userResultAdapter = new UserResultAdapter(medecinesList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(userResultAdapter);
                    }
                }
                else {
                    Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                            "your internet connection ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void addDataToInDemand() {

         @SuppressLint("HardwareIds") final String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

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
                                indemand = new Indemand(drugName,townName,countryName, 1, Arrays.asList(android_id));
                                db.collection("inDemand").add(indemand);
                                Toast.makeText(getContext(), "Your request has been recorded", Toast.LENGTH_SHORT).show();
                            }else {
                                if(indemand.getUserId().contains(android_id)){
                                    Toast.makeText(getContext(), "Your request had already been recorded", Toast.LENGTH_SHORT).show();

                                }else {
                                    db.collection("inDemand").document(indemand.getDocId())
                                            .update(
                                                    "numberRequest", indemand.getNumberRequest() + 1,
                                                          "userId", FieldValue.arrayUnion(android_id)
                                            );
                                    Toast.makeText(getContext(), "Your request has been recorded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                                    "your internet connection ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.client_result_menu, menu);

        MenuItem seachItem = menu.findItem(R.id.search_client_result);
        SearchView searchView = (SearchView) seachItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            public boolean onQueryTextChange(String s) {
                userResultAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
}
