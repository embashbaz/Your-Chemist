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

public class ChemistAdapter extends RecyclerView.Adapter<ChemistAdapter.MyViewHolder> implements Filterable {

    private ArrayList<Medecine> mMedecine;
    private ArrayList<Medecine> mMedecineSearch;
    private NavController navController;

    public ChemistAdapter(ArrayList<Medecine> medecines){
        mMedecine = medecines;
        mMedecineSearch = new ArrayList<>(medecines);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chemist_product_item, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Medecine medecine = mMedecine.get(position);
                holder.statusTxt.setText(medecine.getAvailability());
                holder.genericTxt.setText(medecine.getGenericName());
                holder.scientificTxt.setText(medecine.getScientificName());
                holder.priceTxt.setText(medecine.getPrice()+" "+medecine.getCurrency());
    }

    @Override
    public int getItemCount() {
        return mMedecine.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
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
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView scientificTxt, genericTxt, statusTxt, priceTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            scientificTxt = itemView.findViewById(R.id.scientific_item_chemist);
            genericTxt = itemView.findViewById(R.id.generic_name_item_chemist);
            statusTxt = itemView.findViewById(R.id.availability_item_chemist);
            priceTxt = itemView.findViewById(R.id.price_item_chemist);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Medecine med = mMedecine.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", med.getMedUid());
            bundle.putString("sName", med.getScientificName());
            bundle.putString("gName", med.getGenericName());
            bundle.putString("countryMade", med.getCountryMade());
            bundle.putString("currency", med.getCurrency());
            bundle.putString("detailMed", med.getDetailsMed());
            bundle.putString("availability", med.getAvailability());
            bundle.putDouble("price", med.getPrice());

            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_listMedecines_to_medecineDetails, bundle);

        }
    }
}
