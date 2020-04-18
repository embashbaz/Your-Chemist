package com.example.yourchemist.AdapterAndModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

public class ChemistAdapter extends RecyclerView.Adapter<ChemistAdapter.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
