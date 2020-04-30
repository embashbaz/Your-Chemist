package com.example.yourchemist;

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

import com.example.yourchemist.AdapterAndModel.SpinnerValue;
import com.example.yourchemist.Chemist.ChemistAuth;
import com.google.protobuf.Empty;

import static android.text.TextUtils.isEmpty;


public class ClientSearch extends Fragment {

    EditText searchDrug, searchTown, searchArea;
    Button search;
    Spinner country;

    String drugName, townName, areaName, countryName;
    NavController navController;




    public ClientSearch() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).setActionBarTitle("Search Drug");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


            View view = inflater.inflate(R.layout.fragment_client_search, container, false);
            searchDrug = view.findViewById(R.id.medicine_search_et);
            searchTown = view.findViewById(R.id.town_search_et);
            //searchArea = view.findViewById(R.id.area_search_et);
            search = view.findViewById(R.id.search_button);
            country = view.findViewById(R.id.client_search_spinner);
            SpinnerValue values = new SpinnerValue();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, values.spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);

        return view;
        }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !isEmpty(searchDrug.getText().toString()) && !isEmpty(searchTown.getText().toString())
                         && !isEmpty(country.getSelectedItem().toString())){
                    Bundle bundle = new Bundle();
                    drugName = searchDrug.getText().toString().trim().toLowerCase();
                    townName = searchTown.getText().toString().trim().toLowerCase();
//                    areaName = searchArea.getText().toString();
                    countryName = country.getSelectedItem().toString();
                    bundle.putString("drug", drugName);
                    bundle.putString("town", townName);
                    bundle.putString("country", countryName);
                    navController.navigate(R.id.action_clientSearch_to_clientResultList, bundle);

                }else {
                    Toast.makeText(getActivity(),"please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.client_search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.chemist_menu:
                Intent intent = new Intent(getActivity(), ChemistAuth.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
