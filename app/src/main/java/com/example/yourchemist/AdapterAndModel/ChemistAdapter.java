package com.example.yourchemist.AdapterAndModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

import java.util.ArrayList;

public class ChemistAdapter extends RecyclerView.Adapter<ChemistAdapter.MyViewHolder> {

    private ArrayList<Medecine> mMedecine  = new ArrayList<>();

    public ChemistAdapter(ArrayList<Medecine> medecines){
        mMedecine = medecines;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView scientificTxt, genericTxt, statusTxt, priceTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            scientificTxt = itemView.findViewById(R.id.scientific_item_chemist);
            genericTxt = itemView.findViewById(R.id.generic_name_item_chemist);
            statusTxt = itemView.findViewById(R.id.availability_item_chemist);
            priceTxt = itemView.findViewById(R.id.price_item_chemist);

        }
    }
}
