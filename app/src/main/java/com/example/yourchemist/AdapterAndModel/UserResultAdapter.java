package com.example.yourchemist.AdapterAndModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

import java.util.ArrayList;

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.MyViewModel> {

    private ArrayList<Medecine> mMedecine  = new ArrayList<>();
    private NavController navController;
    public UserResultAdapter(ArrayList<Medecine> medecine){
        mMedecine = medecine;
    }

    @NonNull
    @Override
    public UserResultAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_search_item, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserResultAdapter.MyViewModel holder, int position) {
        Medecine medecine = mMedecine.get(position);
        holder.phoneTxt.setText(medecine.getPhone());
        holder.adressTxt.setText(medecine.getAdress());
        holder.statusTxt.setText("");
        holder.drugTxt.setText(medecine.getScientificName());
        holder.chemistTxt.setText(medecine.getName());
    }

    @Override
    public int getItemCount() {
        return mMedecine.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView chemistTxt, drugTxt, statusTxt, adressTxt, phoneTxt;
        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            chemistTxt = itemView.findViewById(R.id.chemist_item_user);
            drugTxt = itemView.findViewById(R.id.medecine_item_user);
            statusTxt = itemView.findViewById(R.id.status_item_user);
            adressTxt = itemView.findViewById(R.id.adress_item_user);
            phoneTxt = itemView.findViewById(R.id.phone_txt_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Medecine med = mMedecine.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", med.getMedUid());
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_clientResultList_to_clientSearchDetails, bundle);

        }
    }
}
