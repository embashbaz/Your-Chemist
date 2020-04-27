package com.example.yourchemist.Chemist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.Chemist;
import com.example.yourchemist.AdapterAndModel.InDemandAdapter;
import com.example.yourchemist.AdapterAndModel.Indemand;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InDemandList extends Fragment {

    private ArrayList<Indemand> inDemandList = new ArrayList<>();
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private TextView noData;
    private String uid;
    private InDemandAdapter inDemandAdapter;

    public InDemandList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_in_demand_list, container, false);
        noData = view.findViewById(R.id.no_data_in_demand);
        recyclerView = view.findViewById(R.id.in_demand_recycler);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setData();
    }

    private void setData() {
        DocumentReference docRef = db.collection("pharmacies").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Chemist mChemist = documentSnapshot.toObject(Chemist.class);
                setDataIndemand(mChemist);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                        "your internet connection ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setDataIndemand(Chemist mChemist){
        db.collection("inDemand")
                .whereEqualTo("countryName", mChemist.getCountry())
                .whereEqualTo("townName", mChemist.getTown())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        inDemandList.add(document.toObject(Indemand.class));
                        // Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    if(inDemandList.size() == 0){
                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }else {
                        inDemandAdapter = new InDemandAdapter(inDemandList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(inDemandAdapter);
                    }
                }else {
                    Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                            "your internet connection ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
