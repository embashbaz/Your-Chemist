package com.example.yourchemist.Chemist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourchemist.AdapterAndModel.Chemist;
import com.example.yourchemist.AdapterAndModel.ChemistAdapter;
import com.example.yourchemist.AdapterAndModel.Medecine;
import com.example.yourchemist.ClientSearch;
import com.example.yourchemist.MainActivity;
import com.example.yourchemist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMedecines extends Fragment {

    private NavController navController;
    private ArrayList<Medecine> medecineList = new ArrayList<>();
    private String uid;
    private FirebaseFirestore db;
    private String TAG = "ListMedicine fragment";
    private RecyclerView recyclerView;
    private TextView noData;
    private ChemistAdapter chemistAdapter;

    public ListMedecines() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_medecines, container, false);
        noData = view.findViewById(R.id.chemist_no_data_txt);
        recyclerView = view.findViewById(R.id.chemist_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();
        queryForChemist();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_medecine_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_medicine_menu:
                navController.navigate(R.id.action_listMedecines_to_medecineDetails);
                break;
            case R.id.in_demand_menu:
                navController.navigate(R.id.action_listMedecines_to_inDemandList);
        }
        return super.onOptionsItemSelected(item);

    }

    private void queryForChemist(){

        db.collection("Medicine")
                .whereEqualTo("uidDb", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            medecineList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Medecine med = document.toObject(Medecine.class);
                                med.setMedUid(document.getId());
                                medecineList.add(med);
                            }
                            if(medecineList.size() == 0){
                                noData.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.INVISIBLE);
                            }else {
                                chemistAdapter = new ChemistAdapter(medecineList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(chemistAdapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "Something went wrong, try again later, or check " +
                                    "your internet connection ", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }
}
