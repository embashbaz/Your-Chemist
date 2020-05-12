package com.example.yourchemist.AdapterAndModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

import java.util.ArrayList;
import java.util.List;

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.MyViewModel> implements Filterable {

    private ArrayList<Medecine> mMedecine;
    private ArrayList<Medecine> mMedecineSearch;
    private NavController navController;
    public UserResultAdapter(ArrayList<Medecine> medecine){
        mMedecine = medecine;
        mMedecineSearch = new ArrayList<>(medecine);
        notifyDataSetChanged();
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
        holder.adressTxt.setText(medecine.getTown()+", "+medecine.getArea()+", "+medecine.getAdress());
        holder.statusTxt.setText(medecine.getAvailability());
        holder.drugTxt.setText(medecine.getScientificName());
        holder.chemistTxt.setText(medecine.getName());
        holder.priceTxt.setText(medecine.getPrice()+medecine.getCurrency());
    }

    @Override
    public int getItemCount() {
        return mMedecine.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Medecine> search = new ArrayList<>();
                if(charSequence == null || charSequence.length() == 0 ){
                    search.addAll(mMedecineSearch);
                }else {
                    String pattern = charSequence.toString().toLowerCase().trim();
                    for(Medecine item: mMedecineSearch){
                        if(item.getGenericName().toLowerCase().contains(pattern) || item.getGenericName().toLowerCase().contains(pattern)
                                || item.getAvailability().toLowerCase().contains(pattern)){
                            search.add(item);
                        }
                    }

                }
                FilterResults results = new FilterResults();
                results.values = search;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mMedecine.clear();
                mMedecine.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewModel extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView chemistTxt, drugTxt, statusTxt, adressTxt, phoneTxt,priceTxt;
        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            chemistTxt = itemView.findViewById(R.id.chemist_item_user);
            drugTxt = itemView.findViewById(R.id.medecine_item_user);
            statusTxt = itemView.findViewById(R.id.status_item_user);
            adressTxt = itemView.findViewById(R.id.adress_item_user);
            phoneTxt = itemView.findViewById(R.id.phone_txt_item);
            priceTxt = itemView.findViewById(R.id.price_txt_item_user);
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
